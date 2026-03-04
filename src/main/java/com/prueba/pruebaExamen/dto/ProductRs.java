package com.prueba.pruebaExamen.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO de Respuesta para Productos (Response Strategy).
 * Define la estructura de datos que se enviará al cliente, asegurando el desacoplamiento
 * de la entidad interna.
 */
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
    //CON LA RECORD SUPLANTO LOS GETTER Y LAS DE MAS ETIQUETAS
public record ProductRs (

                /**
                 * Nombre comercial del producto.
                 */
                String name,

                /**
                 * Precio de venta al público.
                 */
                 BigDecimal price,

                /**
                 * Cantidad actual disponible en el inventario.
                 */
                 Integer stock
){}

