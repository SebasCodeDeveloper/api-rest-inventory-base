package com.prueba.pruebaExamen.exception;

/**
 * Excepción específica para errores de lógica en el dominio de Usuarios.
 */
public class UserException extends BaseBusinessException {

    /**
     * @param message Detalle del error de usuario.
     * @param type    Categoría de error definida en BusinessErrorType.
     */
    public UserException(String message, BusinessErrorType type) {
        super(message, type);
    }
}