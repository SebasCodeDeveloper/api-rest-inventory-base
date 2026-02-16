package com.prueba.pruebaExamen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidad que representa una orden de compra en el sistema.
 * Relaciona a un usuario con sus transacciones.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue
    private UUID id;

    /**
     * Relación con el usuario que realizó la compra.
     * Muchos pedidos pueden pertenecer a un mismo usuario.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Relación uno a muchos con los detalles de la orden.
     * 'mappedBy = "order"' indica que la relación la controla el campo 'order' en OrderDetail.
     * 'cascade = CascadeType.ALL' asegura que al guardar una orden, sus detalles se guardan automáticamente.
     */

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> details = new ArrayList<>();
    /**
     * Monto total de la orden.
     */
    @Column(nullable = false)
    private Integer total;

    /**
     * Estado actual del pedido manejado por un Enum para asegurar consistencia.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    /**
     * Fecha de creación del registro. No se puede actualizar una vez creada.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Método de ciclo de vida de JPA para asignar la fecha y estado inicial automáticamente.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = Status.CREATED; // Estado inicial por defecto
        }
    }

    /**
     * Estados posibles de una orden para evitar entradas de texto inválidas.
     */
    public enum Status {
        CREATED, PAID, CANCELLED
    }

    // Agrega esto dentro de tu clase Order.java

    /**
     * Calcula el total sumando los subtotales de todos los detalles.
     */
    public void calculateTotal() {
        this.total = this.details.stream()
                .mapToInt(OrderDetail::getSubtotal)
                .sum();
    }
}