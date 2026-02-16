package com.prueba.pruebaExamen.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia de datos (DTO) para las solicitudes de usuario.
 * Define las reglas de validación que deben cumplirse antes de procesar cualquier operación.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRq {

    /**
     * Identificador opcional para operaciones de actualización.
     */
    private String id;

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
    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "La edad debe ser Mayor o igual a 18")
    private Integer age;
}