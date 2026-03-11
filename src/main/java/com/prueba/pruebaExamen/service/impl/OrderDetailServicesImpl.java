package com.prueba.pruebaExamen.service.impl;

import com.prueba.pruebaExamen.dto.*;
import com.prueba.pruebaExamen.entity.*;
import com.prueba.pruebaExamen.exception.*;
import com.prueba.pruebaExamen.repository.OrderDetailRepository;
import com.prueba.pruebaExamen.service.OrderDetailServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Lógica de negocio para el reporte y gestión de detalles de órdenes.
 * Centraliza la transformación de datos planos de la base de datos a estructuras jerárquicas.
 */
@Service
@RequiredArgsConstructor
public class OrderDetailServicesImpl implements OrderDetailServices {

    private final OrderDetailRepository orderDetailRepository;

    /**
     * Recupera y agrupa los detalles pertenecientes a una orden específica mediante su UUID.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderReportRs> getByOrderId(UUID uuid) {
        // Se filtran los detalles en memoria que coinciden con el identificador de la orden
        List<OrderDetail> details = orderDetailRepository.findAll().stream()
                .filter(d -> d.getOrder().getId().equals(uuid))
                .toList();

        if (details.isEmpty()) {
            throw new OrderDetailException("La orden solicitada no existe o no tiene detalles registrados",
                    BusinessErrorType.NOT_FOUND);
        }

        return groupDetails(details);
    }

    /**
     * Genera un reporte global de todas las órdenes en el sistema, organizadas por encabezado y productos.
     */
    @Override
    public List<OrderReportRs> getOrderReport() {
        List<OrderDetail> allDetails = orderDetailRepository.findAll();

        // Validación de existencia de datos antes de procesar el agrupamiento
        if (allDetails.isEmpty()) {
            throw new OrderDetailException("No se encontraron registros de órdenes en el sistema",
                    BusinessErrorType.NOT_FOUND);
        }

        return groupDetails(allDetails);
    }

    /**
     * Filtra el historial de órdenes basándose en el correo electrónico del usuario proporcionado en el request.
     */
    @Override
    public List<OrderReportRs> getByEmail(GetOrderByEmailRq request) {
        // Filtrado funcional para extraer únicamente los registros vinculados al email del cliente
        List<OrderDetail> filteredDetails = orderDetailRepository.findAll().stream()
                .filter(d -> d.getOrder().getUser().getEmail().equalsIgnoreCase(request.getEmail()))
                .toList();

        if (filteredDetails.isEmpty()) {
            throw new OrderDetailException("No existen órdenes vinculadas al email: " + request.getEmail(),
                    BusinessErrorType.NOT_FOUND);
        }

        return groupDetails(filteredDetails);
    }

    /**
     * Motor de transformación (Mapper) que convierte una lista plana de detalles JPA
     * en una estructura organizada de tipo Maestro-Detalle.
     * * @param details Lista de filas recuperadas de la tabla order_details.
     * @return Lista de órdenes agrupadas con sus respectivos productos anidados.
     */
    private List<OrderReportRs> groupDetails(List<OrderDetail> details) {
        return details.stream()
                // Se agrupan los registros en un Map empleando el ID de la orden como llave técnica
                .collect(Collectors.groupingBy(d -> d.getOrder().getId()))
                .values().stream()
                .map(orderGroup -> {
                    // Extracción de metadatos comunes desde el primer elemento del grupo
                    OrderDetail first = orderGroup.get(0);
                    Order order = first.getOrder();

                    // Construcción del DTO de encabezado con su lista interna de productos
                    return new OrderReportRs(
                            order.getId(),
                            order.getUser().getEmail(),
                            order.getStatus(),
                            order.getTotal(),
                            order.getCreatedAt(),
                            orderGroup.stream()
                                    .map(d -> new ProductItemRs(
                                            d.getProduct().getName(),
                                            d.getQuantity(),
                                            d.getUnitPrice(),
                                            d.getSubtotal()))
                                    .toList()
                    );
                })
                .toList();
    }
}