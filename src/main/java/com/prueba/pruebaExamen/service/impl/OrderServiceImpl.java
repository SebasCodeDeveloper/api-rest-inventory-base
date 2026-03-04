package com.prueba.pruebaExamen.service.impl;

import com.prueba.pruebaExamen.dto.*;
import com.prueba.pruebaExamen.entity.*;
import com.prueba.pruebaExamen.exception.BusinessErrorType;
import com.prueba.pruebaExamen.exception.OrderException;
import com.prueba.pruebaExamen.repository.OrderRepository;
import com.prueba.pruebaExamen.repository.ProductRepository;
import com.prueba.pruebaExamen.repository.UserRepository;
import com.prueba.pruebaExamen.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de órdenes.
 * Maneja la creación, obtención, pago y cancelación de órdenes.
 * Utiliza transacciones para mantener la consistencia de datos.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderRs create(OrderRq request) {

        // Verificar si el usuario existe
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new OrderException("Usuario no encontrado con el email proporcionado",
                        BusinessErrorType.NOT_FOUND));

        // Crear nueva orden y asociarla al usuario
        Order order = new Order();
        order.setUser(user);

        // Recorrer los productos solicitados en la orden
        for (OrderDetailRq item : request.items()) {

            // Buscar producto por ID
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new OrderException("Producto no encontrado", BusinessErrorType.NOT_FOUND));

            // Validar que haya suficiente stock
            if (product.getStock() < item.quantity()) {
                throw new OrderException("Stock insuficiente para el producto: " + product.getName(), BusinessErrorType.UNPROCESSABLE);
            }

            // Calcular subtotal del producto (precio * cantidad)
            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(item.quantity()));

            // Descontar el stock del producto
            product.setStock(product.getStock() - item.quantity());

            // Crear detalle de la orden
            OrderDetail detail = new OrderDetail();
            detail.setProduct(product);
            detail.setQuantity(item.quantity());
            detail.setUnitPrice(product.getPrice());
            detail.setSubtotal(subtotal);

            // Agregar detalle a la orden
            order.addDetail(detail);
        }

        // Calcular total de la orden sumando los subtotales
        order.calculateTotal();

        // Guardar la orden en la base de datos
        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }


    //buscar onda orden por id
    @Override
    @Transactional(readOnly = true)
    public OrderRs getById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderException("Orden no encontrada",
                                BusinessErrorType.NOT_FOUND));
        return mapToResponse(order);
    }

    //buscar una orden por email
    @Override
    @Transactional(readOnly = true)
    public List<OrderRs> getByEmail(GetOrderByEmailRq request) {

        if (request.getEmail() == null) {
            throw new OrderException("Email es requerido",
                    BusinessErrorType.BAD_REQUEST);
        }

        List<Order> orders = orderRepository.findByUserEmail(request.getEmail());

        if (orders.isEmpty()) {
            throw new OrderException(
                    "No existen órdenes para el email proporcionado",
                    BusinessErrorType.NOT_FOUND
            );
        }

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<OrderRs> getAll() {
        // Obtener todas las órdenes y mapearlas a DTO
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderRs pay(UUID id) {
        // Buscar orden por ID
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Orden no encontrada", BusinessErrorType.NOT_FOUND));

        //Validar si la orden esta en estado paid
        if (order.getStatus() == OrderStatus.PAID) {
            throw new OrderException("Esta orden ya está pagada", BusinessErrorType.UNPROCESSABLE);
        }

        // Validar que solo órdenes en estado CREATED puedan pagarse
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new OrderException("Solo órdenes en estado CREATED se pueden pagar", BusinessErrorType.UNPROCESSABLE);
        }

        // Cambiar estado de la orden a PAID
        order.setStatus(OrderStatus.PAID);

        return mapToResponse(order);
    }

    @Override
    public OrderRs cancel(UUID id) {
        // Buscar orden por ID
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Orden no encontrada", BusinessErrorType.NOT_FOUND));

        // Si la orden NO está en estado inicial, lanzamos la excepción de inmediato
        if (order.getStatus() != OrderStatus.CREATED) {
            String mensaje = (order.getStatus() == OrderStatus.PAID) ? "No se puede cancelar una orden pagada" :
                    (order.getStatus() == OrderStatus.CANCELLED) ? "Esta orden ya está cancelada" :
                            "No se puede cancelar una orden en proceso";

            throw new OrderException(mensaje, BusinessErrorType.UNPROCESSABLE);
        }

        // Devolver el stock de los productos al inventario
        for (OrderDetail detail : order.getDetails()) {
            Product product = detail.getProduct();
            product.setStock(product.getStock() + detail.getQuantity());
        }

        // Cambiar estado de la orden a CANCELLED
        order.setStatus(OrderStatus.CANCELLED);

        return mapToResponse(order);
    }

    private OrderRs mapToResponse(Order order) {
        // Convertimos la lista de entidades Detail a lista de DTOs DetailRs
        List<OrderDetailRs> detailList = order.getDetails().stream()
                .map(detail -> new OrderDetailRs(
                        detail.getProduct().getName(),
                        detail.getQuantity(),
                        detail.getUnitPrice(),
                        detail.getSubtotal()
                ))
                .toList(); // En Java 16+ usa .toList(), es más limpio que .collect(Collectors.toList())

        // Retornamos el DTO de la orden
        return new OrderRs(
                order.getId(),
                order.getUser().getEmail(),
                order.getStatus(),
                order.getTotal(),
                order.getCreatedAt(),
                detailList
        );
    }


}
