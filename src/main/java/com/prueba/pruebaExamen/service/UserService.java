package com.prueba.pruebaExamen.service;

import com.prueba.pruebaExamen.dto.UserRq;
import com.prueba.pruebaExamen.dto.UserRs;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Contrato de servicios para la gestión de usuarios.
 * Define la abstracción de la lógica de negocio para desacoplar los controladores
 * de la implementación técnica.
 */
public interface UserService {

    /**
     * Define la operación de registro de un nuevo usuario.
     * @param request Objeto con los datos de entrada validados.
     * @return DTO de respuesta con los datos procesados.
     */
    UserRs userCreate(@NonNull UserRq request);

    /**
     * Define la operación para recuperar el catálogo completo de usuarios.
     * @return List de UserRs.
     */
    List<UserRs> getAllUsers();

    /**
     * Define la búsqueda de un recurso específico mediante su identificador único.
     * @param id Identificador de tipo UUID.
     * @return DTO con la información del usuario encontrado.
     */
    UserRs getUsers(UUID id);

    /**
     * Define la operación de actualización de datos de un usuario existente.
     * @param id Identificador del recurso a modificar.
     * @param request Datos actualizados del usuario.
     * @return DTO con la información persistida tras la actualización.
     */
    UserRs update(UUID id, UserRq request);

    /**
     * Define la eliminación de un usuario del sistema.
     * @param id Identificador único del usuario a remover.
     */
    void delete(UUID id);

}