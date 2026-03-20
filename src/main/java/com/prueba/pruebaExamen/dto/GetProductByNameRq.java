package com.prueba.pruebaExamen.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Objeto de transferencia de datos (DTO) utilizado para realizar peticiones de búsqueda un producto.
 * Esta clase encapsula los criterios de filtrado necesarios para recuperar el historial de transacciones desde la capa de servicio.
 */
public record GetProductByNameRq(

        /**
         * NOmbre de prodcuto cuya accion se queier generar.
         * El campo está sujeto a validaciones de obligatoriedad.
         */
        @NotBlank(message = "El nombre es obligatorio")
         String productName
){}

