package com.prueba.pruebaExamen.exception;

/**
 * Excepción específica para errores de lógica en el dominio de Productos.
 */
public class ProductException extends BaseBusinessException {

    /**
     * @param message Mensaje técnico o de negocio.
     * @param type    Categoría de error definida en BusinessErrorType.
     */
    public ProductException(String message, BusinessErrorType type) {
        super(message, type);
    }
}