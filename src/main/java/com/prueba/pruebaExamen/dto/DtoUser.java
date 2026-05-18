package com.prueba.pruebaExamen.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUser {

    /**
     * Identificador opcional para operaciones de actualización.
     */
    private UUID id;

    /**
     * Nombre del usuario. Se valida que no sea nulo ni contenga solo espacios en blanco.
     */
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    /**
     * Correo electrónico con validación de formato estándar RFC 5322.
     */
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es valido")
    private String email;

    /**
     * Edad del usuario. Aplica una regla de negocio para restringir el registro a mayores de edad.
     */
    @NotBlank(message = "El número de celular es obligatorio")
    @Pattern(
    regexp = "^3\\d{9}$",
    message = "El número de celular debe iniciar con 3 y tener 10 dígitos")
    private String numero;

    /**
     * Lista de ordenes para la validacion de usuarios asociados a una orden
     */
    private List<Object> orders;
}
