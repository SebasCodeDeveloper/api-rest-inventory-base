package com.prueba.pruebaExamen.service.impl;

import com.prueba.pruebaExamen.dto.*;
import com.prueba.pruebaExamen.entity.*;
import com.prueba.pruebaExamen.exception.*;
import com.prueba.pruebaExamen.repository.OrderRepository;
import com.prueba.pruebaExamen.service.OrderDetailServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Lógica de negocio para el reporte y gestión de detalles de órdenes.
 * Centraliza la transformación de datos planos de la base de datos a estructuras jerárquicas.
 * Ahora el pivote principal son las Órdenes (Order) para admitir registros de sola mano de obra.
 */
@Service
@RequiredArgsConstructor
public class OrderDetailServicesImpl implements OrderDetailServices {

    // Cambiamos el repositorio pivote para asegurar la existencia de todas las cabeceras
    private final OrderRepository orderRepository;

    /**
     * Recupera y agrupa los detalles pertenecientes a una orden específica mediante su UUID.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderReportRs> getByOrderId(UUID uuid) {
        // Buscamos directamente la orden por su ID
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new OrderDetailException("La orden solicitada no existe",
                        BusinessErrorType.NOT_FOUND));

        // Retornamos una lista con el mapeo de esa única orden
        return List.of(mapToOrderReportRs(order));
    }

    /**
     * Genera un reporte global de todas las órdenes en el sistema, organizadas por encabezado y productos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderReportRs> getOrderReport() {
        List<Order> allOrders = orderRepository.findAll();

        // Validación de existencia de datos antes de procesar
        if (allOrders.isEmpty()) {
            throw new OrderDetailException("No se encontraron registros de órdenes en el sistema",
                    BusinessErrorType.NOT_FOUND);
        }

        // Mapeamos cada orden usando nuestro nuevo método transformador
        return allOrders.stream()
                .map(this::mapToOrderReportRs)
                .toList();
    }

    /**
     * Helper encargado de transformar una entidad Order (Maestro) y sus listas internas
     * (Detalles de Productos y Detalles de Trabajo) a un DTO unificado OrderReportRs.
     */
    private OrderReportRs mapToOrderReportRs(Order order) {

        // Mapeo funcional de la lista de productos (manejando de forma segura si viene nula o vacía)
        List<ProductItemRs> productList = new ArrayList<>();
        if (order.getDetails() != null) {
            productList = order.getDetails().stream()
                    .map(d -> new ProductItemRs(
                            d.getProduct() != null ? d.getProduct().getName() : "Producto Liberado",
                            d.getQuantity(),
                            d.getUnitPrice(),
                            d.getSubtotal()))
                    .toList();
        }

        // Mapeo funcional de los trabajos/mano de obra asociados a esta orden
        List<OrderJobDetailRs> jobList = new ArrayList<>();
        if (order.getJobDetails() != null) {
            jobList = order.getJobDetails().stream()
                    .map(j -> new OrderJobDetailRs(j.getJobName(), j.getPrice()))
                    .toList();
        }

        // Construcción del DTO unificado que espera tu Frontend de Angular
        return new OrderReportRs(
                order.getId(),
                order.getUser() != null ? order.getUser().getEmail() : "Usuario Anónimo",
                order.getUser() != null ? order.getUser().getName() : "Usuario Anónimo",
                order.getUser() != null ? order.getUser().getNumero() : "",
                order.getStatus(),
                order.getTotal(),
                order.getCreatedAt(),
                productList,
                jobList
        );
    }
}