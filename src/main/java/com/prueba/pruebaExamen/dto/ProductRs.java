package com.prueba.pruebaExamen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de Respuesta para Productos (Response Strategy).
 * Define la estructura de datos que se enviará al cliente, asegurando el desacoplamiento
 * de la entidad interna.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRs {

    /**
     * Nombre comercial del producto.
     */
    private String name;

    /**
     * Precio de venta al público.
     */
    private Integer price;

    /**
     * Cantidad actual disponible en el inventario.
     */
    private Integer stock;

}