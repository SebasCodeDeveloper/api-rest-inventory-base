package com.prueba.pruebaExamen.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidad que representa una orden de compra en el sistema.
 * Relaciona a un usuario con sus transacciones.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
     * Relación uno a muchos con los detalles de la orden (Productos).
     * 'mappedBy = "order"' indica que la relación la controla el campo 'order' en OrderDetail.
     * 'cascade = CascadeType.ALL' asegura que al guardar una orden, sus detalles se guardan automáticamente.
     */
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> details = new ArrayList<>();

    /**
     * Relación uno a muchos con los detalles de mano de obra o tipo de trabajo realizado.
     * Mantiene la misma consistencia y cascading que el desglose de productos.
     */
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderJobDetail> jobDetails = new ArrayList<>();

    /**
     * Monto total de la orden.
     */
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    /**
     * Estado actual del pedido manejado por un Enum para asegurar consistencia.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

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
            this.status = OrderStatus.CREATED; // Estado inicial por defecto
        }
    }

    /**
     * Calcula el total sumando los subtotales de todos los detalles de productos
     * más los costos de los tipos de trabajo añadidos en caliente.
     */
    public void calculateTotal() {
        BigDecimal totalProducts = this.details.stream()
                .map(OrderDetail::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalJobs = this.jobDetails.stream()
                .map(OrderJobDetail::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.total = totalProducts.add(totalJobs);
    }

    /**
     * Establece el vínculo bidireccional entre la orden principal y un nuevo desglose de producto.
     * El procedimiento asigna la referencia de la orden actual al detalle recibido y lo integra
     * en el listado de componentes de la transacción.
     */
    public void addDetail(OrderDetail detail) {
        detail.setOrder(this);
        this.details.add(detail);
    }

    /**
     * Establece el vínculo bidireccional entre la orden principal y un desglose de tipo de trabajo realizado.
     * Asigna la referencia de la orden actual al detalle de trabajo recibido y lo integra
     * en el listado operacional para el cálculo financiero masivo.
     */
    public void addJobDetail(OrderJobDetail jobDetail) {
        jobDetail.setOrder(this);
        this.jobDetails.add(jobDetail);
    }

}