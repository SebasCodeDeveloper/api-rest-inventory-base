package com.prueba.pruebaExamen.exception;

public enum BusinessErrorType {
    NOT_FOUND,           // Para recursos inexistentes (404)
    BAD_REQUEST,         // Para errores de validación de cliente (400)
    CONFLICT,            // Para peticiones exitosas sin datos (204)
    UNPROCESSABLE,       // 401: No tiene permiso para esta acción
    UNAUTHORIZED,        // Error inesperado en el sistema (500)
    EMAIL_IN_USE,        // Para conflictos de duplicidad (409)
    INTERNAL_SERVER,     // Error inesperado en el sistema (500)
    NO_CONTENT           // Para peticiones exitosas sin datos (204)
}