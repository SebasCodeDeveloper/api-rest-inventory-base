package com.prueba.pruebaExamen.repository;

import com.prueba.pruebaExamen.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Capa de acceso a datos (DAO) para la entidad Product.
 * Al extender JpaRepository, hereda automáticamente métodos como save(), delete() y findAll().
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    /**
     * Busca un producto por nombre para validar duplicados o realizar consultas específicas.
     * Retorna un Optional para asegurar que el servicio maneje correctamente la ausencia del dato
     * y evitar errores de puntero nulo (NullPointerException).
     */
    Optional<Product> findByName(String name);

    /**
     * Recupera una colección de productos  filtradas por nombre del usuario.
     * El método utiliza la convención de nombres de Spring Data JPA para navegar a través
     * de la relación entre el nombre y la entidad Products.
     */
    List<Product> findByNameContainingIgnoreCase(String name);
}