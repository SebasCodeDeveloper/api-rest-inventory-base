package com.prueba.pruebaExamen.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

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
         * La restricción @NotEmpty asegura que la transacción contenga al menos un ítem.
         * El uso de @Valid garantiza que cada elemento de la lista cumpla con sus propias validaciones internas.
         */
        @NotEmpty(message = "La orden debe tener almenos un producto")
        @Valid
        List<OrderDetailRq> items

) {}