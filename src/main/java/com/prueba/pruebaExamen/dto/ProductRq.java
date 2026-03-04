package com.prueba.pruebaExamen.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para la creación y actualización de productos.
 * Centraliza las reglas de validación de entrada para asegurar datos consistentes.
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public record ProductRq(
        /**
         * Identificador opcional para operaciones de actualización.
         */
         String id,

        /**
         * Nombre descriptivo del producto. No puede estar vacío ni ser nulo.
         */
        @NotBlank(message = "El nombre es obligatorio")
         String name,

/**
         * Precio de venta. Se valida que sea un valor estrictamente positivo (> 0).
         */
        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
         Integer price,

/**
         * Cantidad disponible. Se permite 0 (sin stock), pero no valores negativos.
         */
        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
         Integer stock
        ) {
}