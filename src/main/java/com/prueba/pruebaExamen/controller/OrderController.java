package com.prueba.pruebaExamen.controller;

import com.prueba.pruebaExamen.dto.GetOrderByEmailRq;
import com.prueba.pruebaExamen.dto.OrderRq;
import com.prueba.pruebaExamen.dto.OrderRs;
import com.prueba.pruebaExamen.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Componente controlador encargado de gestionar las peticiones HTTP relacionadas con el ciclo de vida de las órdenes.
 * Expone los endpoints necesarios para la creación, consulta y actualización de estados de las transacciones.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Procesa la creación de una nueva orden en el sistema.
     * Valida la estructura del cuerpo de la petición y retorna la información de la orden persistida con estado 201 Created.
     */
    @PostMapping
    public ResponseEntity<OrderRs> create(@RequestBody @Valid OrderRq request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    /**
     * Recupera un listado de órdenes asociadas a una dirección de correo electrónico específica.
     * Utiliza un objeto de petición para asegurar la validación del formato del email antes de la consulta.
     */
    @PostMapping("/email")
    public ResponseEntity<List<OrderRs>> getByEmail(@Valid @RequestBody GetOrderByEmailRq request) {
        return ResponseEntity.ok(orderService.getByEmail(request));
    }

    /**
     * Obtiene el detalle completo de una orden mediante su identificador único (UUID).
     * Retorna la representación de la orden incluyendo su estado actual y desglose de productos.
     */
    @GetMapping("/{id}")
    public OrderRs getById(@PathVariable UUID id) {
        return orderService.getById(id);
    }

    /**
     * Devuelve la colección completa de órdenes registradas en la base de datos.
     */
    @GetMapping
    public ResponseEntity<List<OrderRs>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    /**
     * Ejecuta la transición de estado de una orden hacia el valor PAID (Pagada).
     * El procedimiento valida que la orden cumpla con los requisitos de negocio antes de confirmar el pago.
     */
    @PutMapping("/{id}/pay")
    public ResponseEntity<OrderRs> pay(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.pay(id));
    }

    /**
     * Gestiona la solicitud de cancelación de una orden existente.
     * Este endpoint desencadena la lógica de reversión de stock y actualización de estado a CANCELLED.
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderRs> cancel(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.cancel(id));
    }
}