package com.prueba.pruebaExamen.repository;

import com.prueba.pruebaExamen.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Capa de acceso a datos (DAO) para la entidad Product.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    /**
     * Busca un producto por nombre para validar duplicados o realizar consultas específicas.
     * Retorna un Optional para asegurar que el servicio maneje correctamente la ausencia del dato
     * y evitar errores de puntero nulo (NullPointerException).
     */
    Optional<Product> findByName(String name);
}