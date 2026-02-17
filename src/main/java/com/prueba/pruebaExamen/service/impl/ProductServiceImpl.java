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

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductRs productCreate(ProductRq request) {

        //Garantizar que el nombre no esté duplicado en el sistema
        if (productRepository.findByName(request.getName()).isPresent()) {
            throw new ProductException(
                    "El producto " + request.getName() + " ya está registrado", BusinessErrorType.CONFLICT);
        }

        // Mapeo manual: Convierte el DTO de entrada a la Entidad JPA
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product produtSaved = productRepository.save(product);

        return toRs(produtSaved);
    }

    @Override
    public List<ProductRs> getAllProducts() {
        List<Product> products = productRepository.findAll();

        if (productRepository.findAll().isEmpty()) {
            throw new ProductException("No se encontraron producto registrados en el sistema", BusinessErrorType.NOT_FOUND);
        }

        return products.stream()
                .map(this::toRs)
                .collect(Collectors.toList());
    }

    @Override
    public ProductRs getUser(UUID id) {

        return productRepository.findById(id)
                .map(this::toRs)
                .orElseThrow(() -> new ProductException(
                        "Producto no existente en la base de datos ", BusinessErrorType.NOT_FOUND
                ));
    }

    @Override
    public ProductRs updatProduct(UUID id, ProductRq request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException(
                        "Producto no existente ", BusinessErrorType.NOT_FOUND
                ));

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

    private ProductRs toRs(Product product) {
        ProductRs pr = new ProductRs();
        pr.setName(product.getName());
        pr.setPrice(product.getPrice());
        pr.setStock(product.getStock());

        return pr;
    }
}
