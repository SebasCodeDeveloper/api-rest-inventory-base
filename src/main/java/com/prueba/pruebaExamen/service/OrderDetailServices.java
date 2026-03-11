package com.prueba.pruebaExamen.service;

import com.prueba.pruebaExamen.dto.GetOrderByEmailRq;
import com.prueba.pruebaExamen.dto.OrderReportRs;

import java.util.List;
import java.util.UUID;

/**
 * Contrato de servicios para la generación de reportes detallados de órdenes.
 * Define las operaciones necesarias para transformar y agrupar datos transaccionales
 * en estructuras jerárquicas de tipo Maestro-Detalle.
 */
public interface OrderDetailServices {

    /**
     * Define la recuperación de un reporte agrupado para una orden específica.
     * @param uuid Identificador único de la orden a consultar.
     * @return Listado estructurado con la información de la orden y sus productos.
     */
    List<OrderReportRs> getByOrderId(UUID uuid);

    /**
     * Define la generación del reporte global de todas las transacciones del sistema.
     * @return Listado de todas las órdenes agrupadas jerárquicamente.
     */
    List<OrderReportRs> getOrderReport();

    /**
     * Define la consulta de reportes históricos filtrados por la identidad del cliente.
     * @param request DTO que contiene el correo electrónico del usuario.
     * @return Colección de órdenes agrupadas pertenecientes al email proporcionado.
     */
    List<OrderReportRs> getByEmail(GetOrderByEmailRq request);
}