package com.prueba.pruebaExamen.repository;

import com.prueba.pruebaExamen.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Capa de acceso a datos (DAO) para la entidad Product.
 * Se especializa en la gestión de productos activos para soportar la lógica de borrado lógico.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    /**
     * Busca un producto por nombre exacto.
     * Se usa generalmente para validaciones de unicidad (duplicados).
     */
    Optional<Product> findByName(String name);

    /**
     * Recupera todos los productos que están marcados como activos.
     * 💡 Esta es la consulta principal para el catálogo del Frontend.
     */
    @Query("SELECT p FROM Product p WHERE p.active = true")
    List<Product> findAllActive();

    /**
     * Realiza una búsqueda parcial por nombre ignorando mayúsculas,
     * pero restringida únicamente a productos activos.
     */
    @Query("SELECT p FROM Product p WHERE p.active = true AND LOWER(p.name) LIKE LOWER(concat('%', :name, '%'))")
    List<Product> findByNameContainingIgnoreCaseActive(@Param("name") String name);

    /**
     * Mantiene la compatibilidad con la convención de nombres de Spring Data JPA
     * si se requiere buscar en todo el universo de productos (activos e inactivos).
     */
    List<Product> findByNameContainingIgnoreCase(String name);
}