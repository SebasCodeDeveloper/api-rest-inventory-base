package com.prueba.pruebaExamen.service.impl;

import com.prueba.pruebaExamen.dto.*;
import com.prueba.pruebaExamen.entity.OrderStatus;
import com.prueba.pruebaExamen.entity.Product;
import com.prueba.pruebaExamen.exception.BusinessErrorType;
import com.prueba.pruebaExamen.exception.ProductException;
import com.prueba.pruebaExamen.repository.ProductRepository;
import com.prueba.pruebaExamen.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Lógica de negocio para la gestión de productos.
 * Implementa el desacoplamiento entre controladores y persistencia con lógica de reactivación.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    /**
     * Procesa la creación de un nuevo producto.
     * Si el producto existe pero está inactivo (Soft Delete), lo reactiva con los nuevos datos.
     */
    @Override
    @Transactional
    public ProductRs create(ProductRq request) {

        // 1. Buscamos el producto por nombre en todo el universo (incluyendo inactivos)
        Optional<Product> productOpt = productRepository.findByName(request.name());

        if (productOpt.isPresent()) {
            Product existingProduct = getProduct(request, productOpt);

            Product reactivatedProduct = productRepository.save(existingProduct);
            return toRs(reactivatedProduct);
        }

        // 4. Si el producto no existe para nada, se crea un registro nuevo
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(BigDecimal.valueOf(request.price()));
        product.setStock(request.stock());

        Product productSaved = productRepository.save(product);

        return toRs(productSaved);
    }

    private static @NonNull Product getProduct(ProductRq request, Optional<Product> productOpt) {
        Product existingProduct = productOpt.get();

        // 2. Si el producto ya está activo, lanzamos el error de duplicado normal
        if (existingProduct.isActive()) {
            throw new ProductException(
                    "El producto " + request.name() + " ya está registrado y activo en el catálogo",
                    BusinessErrorType.CONFLICT);
        }

        existingProduct.setActive(true);
        existingProduct.setPrice(BigDecimal.valueOf(request.price()));
        existingProduct.setStock(request.stock());
        return existingProduct;
    }

    /**
     * Recupera todos los productos activos para el catálogo.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductRs> findAll() {
        List<Product> products = productRepository.findAllActive();

        if (products.isEmpty()) {
            throw new ProductException("No se encontraron productos activos...", BusinessErrorType.NOT_FOUND);
        }

        return products.stream().map(this::toRs).collect(Collectors.toList());
    }

    /**
     * Búsqueda filtrada de productos activos por nombre.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductRs> findByName(GetProductByNameRq request) {
        List<Product> products = productRepository.findByNameContainingIgnoreCaseActive(request.productName());

        if (products.isEmpty()) {
            throw new ProductException("No se encontraron productos activos con el nombre: " + request.productName(),
                    BusinessErrorType.NOT_FOUND);
        }

        return products.stream()
                .map(this::toRs)
                .toList();
    }

    /**
     * Localiza un producto por su ID único.
     */
    @Override
    @Transactional(readOnly = true)
    public ProductRs findById(UUID id) {
        return productRepository.findById(id)
                .map(this::toRs)
                .orElseThrow(() -> new ProductException(
                        "Producto no existente en la base de datos ", BusinessErrorType.NOT_FOUND
                ));
    }

    /**
     * Ejecuta el borrado lógico (Soft Delete) validando integridad de órdenes.
     */
    @Override
    @Transactional
    public void delete(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("El producto no existe", BusinessErrorType.NOT_FOUND));

        boolean tieneOrdenesActivas = product.getOrderDetails().stream()
                .anyMatch(detail -> detail.getOrder().getStatus() != OrderStatus.CANCELLED);

        if (tieneOrdenesActivas) {
            throw new ProductException("No se puede eliminar: El producto tiene órdenes activas o pagadas.",
                    BusinessErrorType.CONFLICT);
        }

        product.setActive(false);
        productRepository.save(product);
    }

    /**
     * Actualiza la información de un producto existente.
     */
    @Override
    @Transactional
    public ProductRs update(UUID id, ProductRq request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException(
                        "Producto no existente ", BusinessErrorType.NOT_FOUND
                ));

        // Validación de conflicto de nombres al editar
        if (!product.getName().equals(request.name()) && productRepository.findByName(request.name()).isPresent()) {
            throw new ProductException(
                    "El nombre  " + request.name() + " ya pertenece a otro producto",
                    BusinessErrorType.CONFLICT
            );
        }

        product.setName(request.name());
        product.setPrice(BigDecimal.valueOf(request.price()));
        product.setStock(request.stock());

        Product productSaved = productRepository.save(product);
        return toRs(productSaved);
    }

    /**
     * Mapper de Entidad a DTO con enriquecimiento de estados de órdenes.
     */
    private ProductRs toRs(Product product) {
        List<OrderDetailProductRs> detailDtos = product.getOrderDetails().stream()
                .map(detail -> new OrderDetailProductRs(
                        detail.getId(),
                        detail.getQuantity(),
                        detail.getUnitPrice(),
                        detail.getSubtotal(),
                        detail.getOrder().getStatus()
                ))
                .toList();

        return new ProductRs(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                detailDtos
        );
    }
}