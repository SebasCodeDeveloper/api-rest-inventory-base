package com.prueba.pruebaExamen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Clase entidad que representa el catálogo maestro de trabajos disponibles en la base de datos.
 * Se utiliza para persistir de forma permanente los nombres y tarifas base de los servicios configurados en el sistema.
 */
@Entity
@Table(name = "job_catalog")
@Getter
@Setter
public class JobCatalog {

    /**
     * Identificador único universal (UUID) del registro del catálogo.
     */
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue
    private UUID id;

    /**
     * Nombre descriptivo y único del trabajo obtenido para la configuración del catálogo.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Valor monetario inicial o estándar asignado al trabajo o servicio.
     * Sirve como referencia base antes de ser capturado en la creación de una orden específica.
     */
    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;
}