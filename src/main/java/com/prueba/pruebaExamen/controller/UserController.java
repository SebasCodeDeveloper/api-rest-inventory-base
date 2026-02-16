package com.prueba.pruebaExamen.controller;

import com.prueba.pruebaExamen.dto.UserRq;
import com.prueba.pruebaExamen.dto.UserRs;
import com.prueba.pruebaExamen.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para la gestión de usuarios.
 * Expone los endpoints siguiendo el estándar de arquitectura RESTful.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    /**
     * Crea un nuevo usuario. Retorna estado 201 (Created).
     */
    @PostMapping
    public ResponseEntity<UserRs> create(@RequestBody @Valid UserRq request) {
        return ResponseEntity.status(201).body(service.userCreate(request));
    }

    /**
     * Obtiene un usuario específico por su identificador único (UUID).
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getUsers(id));
    }

    /**
     * Lista todos los usuarios registrados en el sistema.
     */
    @GetMapping
    public ResponseEntity<List<UserRs>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    /**
     * Elimina un usuario por ID. Retorna 204 No Content si la operación es exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Actualiza la información de un usuario existente identificado por su UUID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid UserRq request) {
        return ResponseEntity.ok(service.update(id, request));
    }
}