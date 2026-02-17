package com.prueba.pruebaExamen.exception;

/**
 * Diccionario de errores de negocio.
 * Vincula fallos de lógica con sus respectivos estados de respuesta.
 */
public enum BusinessErrorType {
    NOT_FOUND,           // 404: El recurso solicitado no existe
    BAD_REQUEST,         // 400: Datos de entrada inválidos o mal formados
    CONFLICT,            // 409: Conflicto con el estado actual (ej. duplicados)
    UNPROCESSABLE,       // 422: Error de validación de lógica de negocio
    UNAUTHORIZED,        // 401: El usuario no está autenticado
    EMAIL_IN_USE,        // 409: Variación específica de conflicto para emails
    INTERNAL_SERVER,     // 500: Error inesperado en el servidor
    NO_CONTENT           // 204: Operación exitosa pero no devuelve cuerpo
}