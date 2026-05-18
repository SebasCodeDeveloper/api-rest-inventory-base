package com.prueba.pruebaExamen.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia de datos (DTO) utilizado para realizar peticiones de búsqueda de órdenes asociadas a un usuario.
 * Esta clase encapsula los criterios de filtrado necesarios para recuperar el historial de transacciones desde la capa de servicio.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserOrdersRq {

    /**
     * Dirección de correo electrónico del usuario cuyas órdenes se desean consultar.
     * El campo está sujeto a validaciones de obligatoriedad y formato estándar de email antes de su procesamiento.
     */
    @Email(message = "Debe ingresar un email válido")
    private String email;

    private String name;

    @Pattern(
            regexp = "^3\\d{9}$",
            message = "El número de celular debe iniciar con 3 y tener 10 dígitos")
    private String numero;
}