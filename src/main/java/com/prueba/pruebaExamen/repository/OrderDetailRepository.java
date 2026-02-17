package com.prueba.pruebaExamen.repository;

import com.prueba.pruebaExamen.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Capa de acceso a datos (DAO) para la entidad OrderDetail.
 * Al extender JpaRepository, hereda automáticamente métodos como save(), delete() y findAll().
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {

}
