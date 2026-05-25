package com.prueba.pruebaExamen.repository;

import com.prueba.pruebaExamen.entity.JobCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Capa de acceso a datos (DAO) para la entidad JobCatalog.
 * Al extender JpaRepository, hereda automáticamente métodos como save(), delete() y findAll().
 */
@Repository
public interface JobCatalogRepository extends JpaRepository<JobCatalog, UUID> {

    /**
     * Busca un trabajo en el catálogo por su nombre, ignorando diferencias entre mayúsculas y minúsculas.
     * Esencial para validaciones de integridad de datos antes de insertar o registrar nuevos servicios,
     * asegurando que el servicio maneje correctamente la ausencia del dato a través de un Optional
     * para evitar errores de puntero nulo (NullPointerException) o registros duplicados.
     */
    Optional<JobCatalog> findByNameIgnoreCase(String name);
}