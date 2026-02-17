package com.prueba.pruebaExamen.controller;

import com.prueba.pruebaExamen.dto.ProductRq;
import com.prueba.pruebaExamen.dto.ProductRs;
import com.prueba.pruebaExamen.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;


    @PostMapping
    private ResponseEntity<ProductRs> productCreate(@RequestBody @Valid ProductRq request) {
        return ResponseEntity.status(201).body(productService.productCreate(request));
    }

    @GetMapping
    private ResponseEntity<List<ProductRs>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getUser(id));
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateProcut(@PathVariable UUID id, @RequestBody @Valid ProductRq request ) {
        return ResponseEntity.ok(productService.updatProduct(id, request));
    }



}


