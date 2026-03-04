package com.prueba.pruebaExamen.service;

import com.prueba.pruebaExamen.dto.GetOrderByEmailRq;
import com.prueba.pruebaExamen.dto.OrderRq;
import com.prueba.pruebaExamen.dto.OrderRs;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderRs create(OrderRq request);

    List<OrderRs> getByEmail(GetOrderByEmailRq request);

    OrderRs getById(UUID id);

    List<OrderRs> getAll();

    OrderRs pay(UUID id);

    OrderRs cancel(UUID id);
}
