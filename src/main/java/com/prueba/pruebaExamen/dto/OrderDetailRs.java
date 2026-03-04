package com.prueba.pruebaExamen.dto;

import java.math.BigDecimal;

/**
 * Objeto de transferencia de datos (DTO) que representa el desglose de un producto en la respuesta de una orden.
 * Al estar definido como un record, Java genera automáticamente campos privados, finales y sus respectivos métodos de acceso.
 */
public record OrderDetailRs(

        /**
         * Nombre descriptivo del producto obtenido de la base de datos.
         */
        String productName,

        /**
         * Cantidad de unidades incluidas en esta línea de la orden.
         */
        Integer quantity,

        /**
         * Precio unitario capturado al momento de la transacción para asegurar la integridad histórica.
         */
        BigDecimal unitPrice,

        /**
         * Monto resultante de la multiplicación entre la cantidad y el precio unitario.
         */
        BigDecimal subtotal

) {}