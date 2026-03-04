package com.prueba.pruebaExamen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entidad que representa la tabla 'products' en la base de datos.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    /**
     * Identificador único de tipo UUID.
     */
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue
    private UUID id;

    /**
     * Nombre del producto.
     * Se marca como único para evitar productos duplicados.
     */

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /**
     * Precio unitario del producto.
     */

    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Cantidad disponible en inventario.
     */

    @Column(nullable = false)
    private Integer stock;

}