package com.prueba.pruebaExamen.service;

import com.prueba.pruebaExamen.dto.ProductRq;
import com.prueba.pruebaExamen.dto.ProductRs;

import java.util.List;
import java.util.UUID;

/**
 * Contrato de servicios para la gestión de productos.
 * Define la abstracción de la lógica de negocio para desacoplar los controladores
 * de la implementación técnica.
 */
public interface ProductService {

    /**
     * Define la operación de creacion de un nuevo producto.
     * @param request Objeto con los datos de entrada validados.
     * @return DTO de respuesta con los datos procesados.
     */
    ProductRs create(ProductRq request);

    /**
     * Define la operación para recuperar el catálogo completo de productos.
     * @return List de ProductRs.
     */
    List<ProductRs> findAll();

    /**
     * Define la búsqueda de un recurso específico mediante su identificador único.
     * @param id Identificador de tipo UUID.
     * @return DTO con la información del producto encontrado.
     */
    ProductRs findById(UUID id);

    /**
     * Define la operación de actualización de datos de un producto existente.
     * @param id Identificador del recurso a modificar.
     * @param request Datos actualizados del producto.
     * @return DTO con la información persistida tras la actualización.
     */
    ProductRs update(UUID id, ProductRq request);

    /**
     * Define la eliminación de un prodcuto del sistema.
     * @param id Identificador único del prodcuto a remover.
     */
    void delete(UUID id);
}