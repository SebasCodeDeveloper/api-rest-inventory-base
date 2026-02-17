package com.prueba.pruebaExamen.exception;

import lombok.Getter;

/**
 * Excepción base para el dominio de negocio.
 * Centraliza el manejo de errores mediante tipos específicos (BusinessErrorType).
 */
@Getter
public abstract class BaseBusinessException extends RuntimeException {

    private final BusinessErrorType type;

    /**
     * @param message Descripción técnica del error.
     * @param type    Categoría de error que define el estado HTTP.
     */
    public BaseBusinessException(String message, BusinessErrorType type) {
        super(message);
        this.type = type;
    }
}