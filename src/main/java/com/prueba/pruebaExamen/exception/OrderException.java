package com.prueba.pruebaExamen.exception;

/**
 * Excepción específica para la gestión de órdenes de compra.
 */
public class OrderException extends BaseBusinessException {

    /**
     * @param message Detalle del error de la orden.
     * @param type    Categoría de error (NOT_FOUND, CONFLICT, etc).
     */
    public OrderException(String message, BusinessErrorType type) {
        super(message, type);
    }
}