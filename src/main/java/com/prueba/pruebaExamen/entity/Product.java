package com.prueba.pruebaExamen.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidad que representa la tabla 'products' en la base de datos.
 * Incluye soporte para borrado lógico (Soft Delete) para mantener la integridad de las órdenes.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    /**
     * Estado de visibilidad del producto.
     * Permite realizar un "Soft Delete" para que el producto no aparezca en el inventario
     * activo, pero las órdenes históricas conserven su referencia.
     */
    @Column(nullable = false)
    private boolean active = true;

    /**
     * Relación OneToMany con el detalle de las órdenes.
     */
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderDetail> orderDetails = new ArrayList<>();

}