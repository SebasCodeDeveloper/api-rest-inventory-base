package com.prueba.pruebaExamen.repository;

import com.prueba.pruebaExamen.dto.UserRs;
import com.prueba.pruebaExamen.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Capa de acceso a datos (DAO) para la entidad Order.
 */
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUserId (String userId);
}
