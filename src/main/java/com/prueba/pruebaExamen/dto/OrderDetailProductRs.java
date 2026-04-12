package com.prueba.pruebaExamen.dto;

import com.prueba.pruebaExamen.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO de respuesta especializado para el desglose de productos (Response Strategy).
 * Proporciona una vista simplificada de los detalles de una orden vinculados a un producto,
 * incluyendo el estado de la orden padre para facilitar la lógica de negocio en el Frontend.
 */
public record OrderDetailProductRs(

        /**
         * Identificador único del detalle de la orden.
         */
        UUID id,

        /**
         * Cantidad de unidades del producto registradas en la transacción.
         */
        Integer quantity,

        /**
         * Monto total de la línea de detalle (quantity * unitPrice).
         */
        BigDecimal subtotal,

        /**
         * Precio unitario capturado al momento de realizar la orden (precio histórico).
         */
        BigDecimal unitPrice,

        /**
         * Estado actual de la orden a la que pertenece este detalle.
         * Crucial para que la UI determine si el producto puede ser eliminado o editado.
         */
        OrderStatus orderStatus
) {}