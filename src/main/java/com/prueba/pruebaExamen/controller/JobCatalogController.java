package com.prueba.pruebaExamen.controller;

import com.prueba.pruebaExamen.entity.JobCatalog;
import com.prueba.pruebaExamen.repository.JobCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Componente controlador encargado de gestionar las peticiones HTTP relacionadas con el catálogo de trabajos disponibles.
 * Expone los endpoints necesarios para la consulta y visualización de los servicios configurados en el sistema.
 */
@RestController
@RequestMapping("/api/job-catalog")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class JobCatalogController {

    private final JobCatalogRepository jobCatalogRepository;

    /**
     * Devuelve la colección completa de trabajos registrados en la base de datos.
     * Retorna la representación del catálogo de forma global para su uso en los formularios del sistema.
     */
    @GetMapping
    public ResponseEntity<List<JobCatalog>> getAllCatalog() {
        return ResponseEntity.ok(jobCatalogRepository.findAll());
    }
}