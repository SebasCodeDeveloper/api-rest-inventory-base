package com.prueba.pruebaExamen.exception;

/**
 * Excepción específica para errores en el dominio de detalles de órdenes.
 */
public class OrderDetailException extends BaseBusinessException {

    /**
     * @param message Descripción del error.
     * @param type    Tipo de error de negocio (NOT_FOUND, CONFLICT, etc).
     */
    public OrderDetailException(String message, BusinessErrorType type) {
        super(message, type);
    }
}