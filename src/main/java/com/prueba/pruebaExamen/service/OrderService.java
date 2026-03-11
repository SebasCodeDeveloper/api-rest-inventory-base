package com.prueba.pruebaExamen.service;

import com.prueba.pruebaExamen.dto.GetOrderByEmailRq;
import com.prueba.pruebaExamen.dto.OrderReportRs;
import com.prueba.pruebaExamen.dto.OrderRq;

import java.util.List;
import java.util.UUID;

/**
 * Contrato de servicios para la gestión integral de órdenes de compra.
 * Define las operaciones abstractas para el procesamiento de transacciones,
 * control de estados y flujos de inventario dentro del sistema.
 */
public interface OrderService {

    /**
     * Define la operación de creación y persistencia de una nueva orden.
     * @param request DTO con la información del cliente y los ítems solicitados.
     * @return DTO de respuesta con el resumen de la orden generada.
     */
    OrderReportRs create(OrderRq request);

    /**
     * Define la recuperación del historial de órdenes vinculadas a una cuenta de correo.
     * @param request Objeto de consulta que contiene el email del usuario.
     * @return Listado de órdenes (OrderRs) asociadas al criterio de búsqueda.
     */
    List<OrderReportRs> getByEmail(GetOrderByEmailRq request);

    /**
     * Define la búsqueda de una orden específica mediante su identificador técnico.
     * @param id Identificador único de tipo UUID.
     * @return DTO con la información detallada de la orden encontrada.
     */
    OrderReportRs getById(UUID id);

    /**
     * Define la operación para obtener el catálogo global de órdenes procesadas.
     * @return List de todas las órdenes registradas en el sistema.
     */
    List<OrderReportRs> getAll();

    /**
     * Define la transición de estado de una orden hacia el cumplimiento del pago.
     * @param id Identificador de la orden a procesar financieramente.
     * @return DTO con la información de la orden tras la actualización de estado.
     */
    OrderReportRs pay(UUID id);

    /**
     * Define la anulación de una orden y la lógica de reversión de recursos.
     * @param id Identificador de la orden que se desea cancelar.
     * @return DTO con el estado final de la transacción cancelada.
     */
    OrderReportRs cancel(UUID id);

    /**
     * Define la eliminación de una orden del sistema.
     * @param id Identificador única orden a remover.
     */
    void delete(UUID id);

}