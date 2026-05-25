package com.prueba.pruebaExamen.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Objeto de transferencia de datos (DTO) utilizado para registrar el detalle de un trabajo dentro de una orden.
 * Esta clase encapsula los criterios necesarios para asociar actividades y costos específicos a la transacción desde la capa de servicio.
 */
public record OrderJobDetailRq(

        /**
         * Nombre del trabajo o servicio cuya acción se quiere generar.
         * El campo está sujeto a validaciones de obligatoriedad.
         */
        @NotBlank(message = "El nombre del trabajo no puede estar vacío")
        String jobName,

        /**
         * Valor monetario asignado al trabajo o servicio a realizar.
         * El campo está sujeto a validaciones de obligatoriedad numérica.
         */
        @NotNull(message = "El precio del trabajo es obligatorio")
        BigDecimal price
) {}