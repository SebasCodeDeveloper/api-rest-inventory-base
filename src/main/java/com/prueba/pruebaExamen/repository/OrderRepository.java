package com.prueba.pruebaExamen.repository;

import com.prueba.pruebaExamen.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Capa de acceso a datos (DAO) especializada en la entidad Order.
 * Al extender JpaRepository, hereda automáticamente la capacidad de realizar
 * operaciones CRUD fundamentales y paginación sobre la tabla de órdenes.
 */
public interface OrderRepository extends JpaRepository<Order, UUID> {

    /**
     * Recupera una colección de órdenes filtradas por la dirección de correo electrónico del usuario.
     * El método utiliza la convención de nombres de Spring Data JPA para navegar a través
     * de la relación entre la orden y la entidad User.
     */
    List<Order> findByUserEmail(String email);

    /**
     * Busca un usuario por su email para validar duplicados o realizar consultas específicas.
     */
    Order findByUserId(UUID userId);
}