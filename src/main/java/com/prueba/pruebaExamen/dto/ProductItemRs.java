package com.prueba.pruebaExamen.dto;

import java.math.BigDecimal;

/**
 * Record que representa el desglose individual de un producto dentro de un reporte de orden.
 * Se utiliza para detallar las métricas financieras y de inventario de cada artículo
 * en una transacción específica.
 */
public record ProductItemRs(

        /**
         * Nombre descriptivo del producto obtenido del catálogo.
         */
        String productName,

        /**
         * Cantidad de unidades adquiridas en la línea de detalle.
         */
        Integer quantity,

        /**
         * Precio unitario capturado en el momento de la creación de la orden.
         * Garantiza la integridad histórica frente a cambios de precio en el catálogo.
         */
        BigDecimal unitPrice,

        /**
         * Monto total de la línea (precio unitario multiplicado por cantidad).
         */
        BigDecimal subTotal
) {
}