package com.prueba.pruebaExamen.controller;

import com.prueba.pruebaExamen.dto.ProductRq;
import com.prueba.pruebaExamen.dto.ProductRs;
import com.prueba.pruebaExamen.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para la creacion de un producto.
 * Expone los endpoints siguiendo el estándar de arquitectura RESTful.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Crea un nuevo producto. Retorna estado 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ProductRs> create(@RequestBody @Valid ProductRq request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    /**
     * Lista todos los productos  registrados en el sistema.
     */
    @GetMapping
    public ResponseEntity<List<ProductRs>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * Obtiene un producto específico por su identificador único (UUID).
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    /**
     * Actualiza la información de un producto existente identificado por su UUID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid ProductRq request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    /**
     * Elimina un producto por ID. Retorna 204 No Content si la operación es exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}


