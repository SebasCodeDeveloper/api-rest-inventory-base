package com.prueba.pruebaExamen.service;

import com.prueba.pruebaExamen.dto.ProductRq;
import com.prueba.pruebaExamen.dto.ProductRs;

import java.util.List;
import java.util.UUID;


public interface ProductService {

    ProductRs productCreate(ProductRq request);

    List<ProductRs> getAllProducts();

    ProductRs getUser(UUID id);

    ProductRs updatProduct(UUID id, ProductRq request);
}