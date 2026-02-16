package com.prueba.pruebaExamen.repository;

import com.prueba.pruebaExamen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Capa de acceso a datos (DAO) para la entidad User.
 * Al extender JpaRepository, hereda automáticamente métodos como save(), delete() y findAll().
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Verifica la existencia de un registro por email.
     * Esencial para validaciones de integridad de datos antes de insertar un nuevo usuario.
     */
    boolean existsByEmail(String email);

}