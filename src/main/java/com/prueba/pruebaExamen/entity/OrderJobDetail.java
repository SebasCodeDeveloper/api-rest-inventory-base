package com.prueba.pruebaExamen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Clase entidad que representa el desglose individual de un trabajo dentro de una orden en la base de datos.
 * Se utiliza para persistir de forma permanente las métricas financieras y de servicios de cada actividad
 * en una transacción específica.
 */
@Entity
@Table(name = "order_job_details")
@Getter
@Setter
public class OrderJobDetail {

    /**
     * Identificador único universal (UUID) del registro de detalle.
     */
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue
    private UUID id;

    /**
     * Relación muchos a uno que asocia este detalle con su orden principal correspondiente.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Nombre descriptivo del trabajo obtenido del catálogo en el momento de la transacción.
     */
    @Column(name = "job_name", nullable = false)
    private String jobName;

    /**
     * Precio asignado al trabajo capturado en el momento de la creación de la orden.
     * Garantiza la integridad histórica frente a cambios de tarifas en el catálogo.
     */
    @Column(nullable = false)
    private BigDecimal price;
}