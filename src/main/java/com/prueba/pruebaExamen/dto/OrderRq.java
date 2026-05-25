package com.prueba.pruebaExamen.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * Objeto de transferencia de datos (DTO) de tipo record que captura la solicitud inicial de una compra.
 * Este componente agrupa la identidad del cliente y el listado de productos requeridos para su procesamiento.
 */
public record OrderRq(

        /**
         * Dirección de correo electrónico del usuario que solicita la orden.
         * El campo es obligatorio y debe cumplir con un formato de email válido para la vinculación con la cuenta.
         */
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El formato del email no es valido")
        String email,

        /**
         * Colección de productos y cantidades que componen el pedido.
         * MODIFICADO: Se remueve @NotEmpty para permitir órdenes basadas únicamente en servicios de mano de obra.
         * El uso de @Valid garantiza que cada elemento de la lista cumpla con sus propias validaciones internas.
         */
        @Valid
        List<OrderDetailRq> items,

        /**
         * Colección opcional de tipos de trabajo o servicios de mano de obra añadidos a la orden.
         * El uso de @Valid garantiza el análisis de las restricciones internas de precio y texto de cada trabajo.
         */
        @Valid
        List<OrderJobDetailRq> jobs
) {

        /**
         * Validación personalizada a nivel de objeto.
         * Garantiza de forma dinámica que la orden contenga al menos un producto O al menos un trabajo.
         * Evita que se envíen órdenes totalmente vacías al sistema.
         */
        @AssertTrue(message = "La orden debe contener al menos un producto o un detalle de trabajo")
        public boolean isHasContenido() {
                boolean tieneProductos = items != null && !items.isEmpty();
                boolean tieneTrabajos = jobs != null && !jobs.isEmpty();
                return tieneProductos || tieneTrabajos;
        }
}