package com.prueba.pruebaExamen.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Objeto de transferencia de datos (DTO) definido como record para capturar los ítems individuales de una orden.
 * Representa la unidad mínima de información necesaria para vincular un producto y su cantidad en una solicitud de compra.
 */
public record OrderDetailRq(

        /**
         * Identificador único del producto (UUID) que el cliente desea adquirir.
         * Este campo es obligatorio para realizar la consulta de precios y stock en la base de datos.
         */
        @NotNull(message = "El id del producto es obligatorio")
        UUID productId,

        /**
         * Valor entero que especifica el número de unidades solicitadas del artículo.
         * La restricción @Min garantiza que el pedido contenga al menos una unidad del producto.
         */
        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser mayor a 0")
        Integer quantity

) {}