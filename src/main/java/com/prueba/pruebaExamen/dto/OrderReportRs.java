package com.prueba.pruebaExamen.dto;

import com.prueba.pruebaExamen.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO de respuesta especializado en el agrupamiento de información de órdenes.
 * Este record transforma la estructura plana de la base de datos en una vista jerárquica
 * de tipo Maestro-Detalle, ideal para reportes ejecutivos y consumo de APIs.
 */
public record OrderReportRs(

        /**
         * Identificador único de la orden (UUID).
         */
        UUID orderId,

        /**
         * Correo electrónico del usuario que generó la transacción.
         */
        String email,

        /**
         * Estado actual de la orden dentro del flujo de negocio (CREATED, PAID, CANCELLED).
         */
        OrderStatus status,

        /**
         * Resuktado todatl de la compra realizada
         */
        BigDecimal total,

        /**
         * Marca de tiempo que indica el momento exacto de la creación del registro en el sistema.
         */
        LocalDateTime createdAt,

        /**
         * Colección de productos asociados a esta orden específica.
         * Permite visualizar múltiples artículos bajo un mismo encabezado de orden.
         */
        List<ProductItemRs> items
) {}