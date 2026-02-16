package com.prueba.pruebaExamen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

/**
 * Entidad que representa el desglose de productos dentro de una orden.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * detalle de orden se refiere a UN solo producto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    /**
     * Subtotal de este renglón (Cantidad * Precio del producto).
     */
    @Column(nullable = false)
    private Integer subtotal;

    /**
     * 💡 Lógica automática: Calcula el subtotal antes de guardar.
     */
    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        if (this.product != null && this.quantity != null) {
            this.subtotal = this.product.getPrice() * this.quantity;
        }
    }
}