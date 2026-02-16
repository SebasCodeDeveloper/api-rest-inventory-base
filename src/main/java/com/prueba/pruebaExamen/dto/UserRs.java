package com.prueba.pruebaExamen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de Respuesta (Response Strategy).
 * Se utiliza para modelar los datos que la API devuelve al cliente,
 * evitando exponer directamente la entidad JPA (User).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRs {

    /**
     * Nombre del usuario retornado en la respuesta.
     */
    private String name;

    /**
     * Correo electrónico del usuario. Sirve como identificador natural en la vista.
     */
    private String email;

    /**
     * Edad del usuario.
     */
    private Integer age;
}