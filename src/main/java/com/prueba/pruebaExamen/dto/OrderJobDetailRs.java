package com.prueba.pruebaExamen.dto;

import java.math.BigDecimal;

/**
 * Record que representa el desglose individual de un trabajo dentro de un reporte de orden.
 * Se utiliza para detallar las métricas financieras y de servicios de cada actividad
 * en una transacción específica.
 */
public record OrderJobDetailRs(

        /**
         * Nombre descriptivo del trabajo obtenido del catálogo.
         */
        String jobName,

        /**
         * Precio asignado al trabajo capturado en el momento de la creación de la orden.
         * Garantiza la integridad histórica frente a cambios de tarifas en el catálogo.
         */
        BigDecimal price
) {}