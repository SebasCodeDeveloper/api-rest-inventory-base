package com.prueba.pruebaExamen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entidad que representa el desglose de productos dentro de una orden.
 * Esta clase gestiona la relación entre artículos individuales, sus cantidades
 * y el registro histórico de precios de venta.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Usará el campo 'id' como referencia
@Table(name = "order_details")
public class OrderDetail {

    /**
     * Identificador único del registro de detalle, gestionado como un UUID.
     */
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue
    private UUID id;

    /**
     * Referencia a la entidad de orden superior a la que se vincula este desglose.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Vinculación con un único producto del catálogo para cada línea de detalle.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Valor entero que indica la cantidad de unidades solicitadas por el cliente.
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Captura del precio unitario proveniente de la tabla de productos al momento de la creación.
     * Este campo preserva la integridad de la transacción frente a variaciones futuras de costo.
     */
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    /**
     * Resultado económico de multiplicar la cantidad por el precio unitario capturado.
     */
    @Column(nullable = false)
    private BigDecimal subtotal;

    /**
     * 💡 Procedimiento de lógica automatizada que asegura la consistencia de los datos financieros.
     * El método extrae el precio vigente del producto y recalcula el subtotal antes de la persistencia.
     */
    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        if (this.product != null && this.quantity != null) {
            // Se establece el precio unitario basándose en el estado actual del producto.
            this.unitPrice = this.product.getPrice();

            // Se determina el subtotal empleando el precio unitario y la cantidad registrada.
            this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
        }
    }
}