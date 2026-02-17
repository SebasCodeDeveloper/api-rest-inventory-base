package com.prueba.pruebaExamen.service.impl;

import com.prueba.pruebaExamen.dto.ProductRq;
import com.prueba.pruebaExamen.dto.ProductRs;
import com.prueba.pruebaExamen.entity.Product;
import com.prueba.pruebaExamen.exception.BusinessErrorType;
import com.prueba.pruebaExamen.exception.ProductException;
import com.prueba.pruebaExamen.repository.ProductRepository;
import com.prueba.pruebaExamen.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Lógica de negocio para la gestión de productos.
 * Implementa el desacoplamiento entre controladores y persistencia.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    /**
     * Procesa la creación de un nuevo producto con validación de unicidad de nombre.
     */
    @Override
    public ProductRs create(ProductRq request) {

        //Garantizar que el nombre no esté duplicado en el sistema
        if (productRepository.findByName(request.getName()).isPresent()) {
            throw new ProductException(
                    "El producto " + request.getName() + " ya está registrado", BusinessErrorType.CONFLICT);
        }

        //Convierte el DTO de entrada a la Entidad JPA
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product produtSaved = productRepository.save(product);

        return toRs(produtSaved);
    }

    /**
     * Recupera todos los productos y valida que la lista no sea vacía.
     */
    @Override
    public List<ProductRs> findAll() {
        List<Product> products = productRepository.findAll();

        // Si el repositorio no retorna datos, se lanza una excepción controlada
        if (productRepository.findAll().isEmpty()) {
            throw new ProductException("No se encontraron producto registrados en el sistema",
                    BusinessErrorType.NOT_FOUND);
        }

        return products.stream()
                .map(this::toRs)
                .collect(Collectors.toList());
    }

    /**
     * Busca un producto mediante su identificador técnico (UUID).
     */
    @Override
    public ProductRs findById(UUID id) {

        return productRepository.findById(id)
                .map(this::toRs)
                .orElseThrow(() -> new ProductException(
                        "Producto no existente en la base de datos ", BusinessErrorType.NOT_FOUND
                ));
    }

    /**
     * Realiza el borrado físico del registro tras validar su existencia.
     */
    @Override
    public void delete(UUID id) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new ProductException("El producto  no existe", BusinessErrorType.NOT_FOUND));
        productRepository.delete(product);

    }

    /**
     * Actualiza un producto existente, permitiendo cambios de productos con validación de conflictos.
     */
    @Override
    public ProductRs update(UUID id, ProductRq request) {

        // Verifica existencia previa del recurso
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException(
                        "Producto no existente ", BusinessErrorType.NOT_FOUND
                ));
        //Si cambia el nombre, este no debe existir en la tabla
        if (!product.getName().equals(request.getName()) && productRepository.findByName(request.getName()).isPresent()) {
            throw new ProductException(
                    "El email " + request.getName() + " ya pertenece a otro usuario",
                    BusinessErrorType.CONFLICT
            );
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product produtSaved = productRepository.save(product);
        return toRs(produtSaved);
    }

    /**
     * Mapper privado para transformar Entidad (JPA) a DTO de Respuesta (PR).
     */
    private ProductRs toRs(Product product) {
        ProductRs pr = new ProductRs();
        pr.setName(product.getName());
        pr.setPrice(product.getPrice());
        pr.setStock(product.getStock());

        return pr;
    }
}
