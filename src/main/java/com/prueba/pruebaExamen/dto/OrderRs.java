package com.prueba.pruebaExamen.dto;

import com.prueba.pruebaExamen.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Objeto de transferencia de datos (DTO) de tipo record que representa la respuesta final de una orden.
 * Este componente consolida la información de identidad, estado financiero y desglose de productos para el cliente.
 */
public record OrderRs(

        /**
         * Identificador único de la orden generado por el sistema (UUID).
         */
        UUID id,

        /**
         * Dirección de correo electrónico del usuario asociado a la transacción.
         */
        String email,

        /**
         * Estado actual de la orden (CREATED, PAID, CANCELLED, etc.) que rige el flujo de negocio.
         */
        OrderStatus status,

        /**
         * Monto total acumulado de la compra, calculado a partir de la sumatoria de los subtotales.
         */
        BigDecimal total,

        /**
         * Marca de tiempo que indica el momento exacto de la creación del registro en el sistema.
         */
        LocalDateTime createdAt,

        /**
         * Listado detallado de los productos adquiridos, incluyendo precios unitarios históricos y cantidades.
         */
        List<OrderDetailRs> items

) {}