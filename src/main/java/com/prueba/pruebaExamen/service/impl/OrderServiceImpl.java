package com.prueba.pruebaExamen.service.impl;

import com.prueba.pruebaExamen.dto.*;
import com.prueba.pruebaExamen.entity.*;
import com.prueba.pruebaExamen.exception.BusinessErrorType;
import com.prueba.pruebaExamen.exception.OrderDetailException;
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

/**
 * Lógica de negocio para la gestión integral de órdenes de compra.
 * Coordina la interacción entre usuarios, productos y la persistencia de transacciones,
 * asegurando la integridad del inventario y el flujo de estados de la orden.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    /**
     * Procesa la creación de una orden, validando stock y calculando importes financieros.
     * Realiza el descuento automático de inventario por cada producto solicitado.
     */
    @Override
    public OrderReportRs create(OrderRq request) {

        // Validación de existencia del cliente mediante su correo electrónico
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new OrderException("Usuario no encontrado con el email proporcionado",
                        BusinessErrorType.NOT_FOUND));

        // Instanciación de la cabecera de la orden vinculada al usuario
        Order order = new Order();
        order.setUser(user);

        // Procesamiento iterativo de los ítems solicitados
        for (OrderDetailRq item : request.items()) {

            // Verificación de existencia del producto en catálogo
            Product product = productRepository.findByName(item.productName())
                    .orElseThrow(() -> new OrderException("Producto no encontrado", BusinessErrorType.NOT_FOUND));

            // Control de disponibilidad física en inventario
            if (product.getStock() < item.quantity()) {
                throw new OrderException("Stock insuficiente para el producto: " + product.getName(),
                        BusinessErrorType.UNPROCESSABLE);
            }

            // Cálculo dinámico del subtotal y actualización de stock
            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(item.quantity()));

            product.setStock(product.getStock() - item.quantity());

            // Construcción del detalle de la transacción
            OrderDetail detail = new OrderDetail();
            detail.setProduct(product);
            detail.setQuantity(item.quantity());
            detail.setUnitPrice(product.getPrice());
            detail.setSubtotal(subtotal);

            // Vinculación bidireccional entre la orden y su detalle
            order.addDetail(detail);
        }

        // Ejecución de lógica de negocio para consolidar el total de la compra
        order.calculateTotal();

        // Persistencia de la orden y retorno del DTO de respuesta
        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    /**
     * Localiza una orden específica empleando su identificador único UUID.
     */
    @Override
    @Transactional(readOnly = true)
    public OrderReportRs getById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("La orden solicitada no existe",
                        BusinessErrorType.NOT_FOUND));
        return mapToResponse(order);
    }

    /**
     * Recupera el historial de órdenes asociadas a una cuenta de correo electrónico.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderReportRs> getByEmail(GetOrderByEmailRq request) {
        List<Order> orders = orderRepository.findByUserEmail(request.getEmail());

        if (orders.isEmpty()) {
            throw new OrderException("No se encontraron registros de órdenes para el email: " + request.getEmail(),
                    BusinessErrorType.NOT_FOUND);
        }

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Obtiene el listado completo de órdenes procesadas en el sistema.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderReportRs> getAll() {
        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new OrderDetailException("No existen registros de órdenes en la base de datos",
                    BusinessErrorType.NOT_FOUND);
        }

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Realiza la transición de estado de una orden a 'PAID' (Pagada).
     * Valida que el estado actual permita completar la transacción financiera.
     */
    @Override
    public OrderReportRs pay(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Orden no encontrada", BusinessErrorType.NOT_FOUND));

        // Verificación de idempotencia (no pagar dos veces)
        if (order.getStatus() == OrderStatus.PAID) {
            throw new OrderException("La transacción ya ha sido completada previamente",
                    BusinessErrorType.UNPROCESSABLE);
        }

        // Restricción de flujo: Solo se paga lo que ha sido creado recientemente
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new OrderException("El estado actual de la orden no permite procesar el pago",
                    BusinessErrorType.UNPROCESSABLE);
        }

        order.setStatus(OrderStatus.PAID);
        return mapToResponse(order);
    }

    /**
     * Cancelar un orden existente frente a sus validaciones.
     */
    @Override
    public OrderReportRs cancel(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Orden no encontrada", BusinessErrorType.NOT_FOUND));

        // Control de estados para evitar cancelaciones de órdenes pagadas o ya anuladas
        if (order.getStatus() != OrderStatus.CREATED) {
            String mensaje = (order.getStatus() == OrderStatus.PAID) ? "No es posible cancelar una transacción ya pagada" :
                    (order.getStatus() == OrderStatus.CANCELLED) ? "La orden ya se encuentra en estado cancelado" :
                    "Operación no permitida para el estado actual de la orden";

            throw new OrderException(mensaje, BusinessErrorType.UNPROCESSABLE);
        }

        for (OrderDetail detail : order.getDetails()) {
            Product product = detail.getProduct();
            if (product != null) {
                product.setStock(product.getStock() + detail.getQuantity());
            }
        }

        order.setStatus(OrderStatus.CANCELLED);

        return mapToResponse(orderRepository.save(order));
    }

    /**
     * Ejecuta la anulación de una orden y realiza la reversión de stock al inventario.
     */
    @Override
    public OrderReportRs update(UUID id, OrderReportRs request) {
        // Validacion de existencia de order
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("La orden con ID " + id + " no existe",
                        BusinessErrorType.NOT_FOUND));

        // Validacion de negocio, solo se puede editar ordendes en estado creado
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new OrderException("Solo se pueden modificar órdenes en estado CREATED",
                    BusinessErrorType.UNPROCESSABLE);
        }

        //Si el email en el request es distinto al de la orden actual, buscamos el nuevo usuario
        if (!order.getUser().getEmail().equals(request.email())) {
            User newUser = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new OrderException("El nuevo usuario con email " + request.email() + " no existe",
                            BusinessErrorType.NOT_FOUND));
            order.setUser(newUser);
        }

        // Devolver el inventario.
        for (OrderDetail detail : order.getDetails()) {
            Product product = detail.getProduct();
            product.setStock(product.getStock() + detail.getQuantity());
        }
        order.getDetails().clear();

        for (ProductItemRs newItem : request.items()) {
            Product product = productRepository.findByName(newItem.productName())
                    .orElseThrow(() -> new OrderException("Producto no encontrado: " + newItem.productName(),
                            BusinessErrorType.NOT_FOUND));

            // Validacion  de  stock disponible
            if (product.getStock() < newItem.quantity()) {
                throw new OrderException("Stock insuficiente para: " + product.getName() +
                        " (Disponible: " + product.getStock() + ")",
                        BusinessErrorType.UNPROCESSABLE);
            }
            product.setStock(product.getStock() - newItem.quantity());

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(newItem.quantity()));

            // Crear nuevo detalle
            OrderDetail detail = new OrderDetail();
            detail.setProduct(product);
            detail.setQuantity(newItem.quantity());
            detail.setUnitPrice(product.getPrice());
            detail.setSubtotal(subtotal);

            // Vincular a la orden
            order.addDetail(detail);
        }


        order.calculateTotal();
        Order updatedOrder = orderRepository.save(order);

        return mapToResponse(updatedOrder);
    }

    /**
     * Realiza el borrado de la orden y devolviendo los productos a su posicion  anterios .
     */
    @Override
    public void delete(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Orden no encontrada", BusinessErrorType.NOT_FOUND));

        // Control de estados para evitar cancelaciones de órdenes pagadas o ya anuladas
        if (order.getStatus() != OrderStatus.CREATED) {
            String mensaje = (order.getStatus() == OrderStatus.PAID) ? "Las órdenes pagadas no permiten eliminación." :
                    (order.getStatus() == OrderStatus.CANCELLED) ? "Las órdenes canceladas no permiten eliminación." :
                    "Operación no permitida para el estado actual de la orden";

            throw new OrderException(mensaje, BusinessErrorType.UNPROCESSABLE);
        }

        // Procedimiento de devolución de stock físico al catálogo de productos y liberación
        for (OrderDetail detail : order.getDetails()) {
            Product product = detail.getProduct();
            if (product != null) {
                product.setStock(product.getStock() + detail.getQuantity());
                detail.setProduct(null);
            }
        }

        order.setUser(null);
        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.delete(order);
    }


    /**
     * Mapeador interno para transformar la entidad Order y sus detalles a una respuesta DTO jerárquica.
     */
    private OrderReportRs mapToResponse(Order order) {
        // Transformación funcional de la lista de detalles de la entidad a DTOs de respuesta
        List<ProductItemRs> detailList = order.getDetails().stream()
                .map(detail -> {
                    String productName = (detail.getProduct() != null) ? detail.getProduct().getName() : "Producto Liberado";
                    return new ProductItemRs(
                            productName,
                            detail.getQuantity(),
                            detail.getUnitPrice(),
                            detail.getSubtotal()
                    );
                })
                .toList();

        // Manejo preventivo si el usuario es nulo tras una cancelación
        String userEmail = (order.getUser() != null) ? order.getUser().getEmail() : "Usuario Liberado";

        return new OrderReportRs(
                order.getId(),
                userEmail,
                order.getStatus(),
                order.getTotal(),
                order.getCreatedAt(),
                detailList
        );
    }
}