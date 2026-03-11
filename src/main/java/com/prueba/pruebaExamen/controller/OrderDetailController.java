package com.prueba.pruebaExamen.controller;

import com.prueba.pruebaExamen.dto.GetOrderByEmailRq;
import com.prueba.pruebaExamen.dto.OrderReportRs;
import com.prueba.pruebaExamen.service.OrderDetailServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST encargado de exponer los servicios de reporte y consulta de detalles de órdenes.
 * Proporciona endpoints para la recuperación de información estructurada y agrupada
 * para el consumo de clientes externos y sistemas de auditoría.
 */
@RestController
@RequestMapping("/api/details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailServices orderDetailServices;

    /**
     * Endpoint para recuperar el reporte global de todas las órdenes registradas.
     * @return Una lista de órdenes agrupadas con sus respectivos detalles de productos.
     */
    @GetMapping()
    public List<OrderReportRs> getByAll() {
        return orderDetailServices.getOrderReport();
    }

    /**
     * Consulta una orden específica y su desglose de productos mediante su identificador único (UUID).
     * @param id Identificador técnico de la orden.
     * @return Lista (maestro-detalle) de la orden solicitada.
     */
    @GetMapping("/{id}")
    public List<OrderReportRs> getById(@PathVariable UUID id) {
        return orderDetailServices.getByOrderId(id);
    }

    /**
     * Endpoint de consulta avanzada que filtra el historial de órdenes basándose en un correo electrónico.
     * Utiliza el método POST para proteger la integridad de la consulta y manejar el cuerpo del request.
     * @param request DTO que contiene el email del cliente a consultar.
     * @return Listado de órdenes vinculadas al correo proporcionado, organizadas por encabezado y productos.
     */
    @PostMapping
    public List<OrderReportRs> getByOrderEmail(@Valid @RequestBody GetOrderByEmailRq request ) {
        return orderDetailServices.getByEmail(request);
    }


}