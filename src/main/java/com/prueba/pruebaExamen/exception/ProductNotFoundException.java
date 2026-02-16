    package com.prueba.pruebaExamen.exception;

    import lombok.Getter;

    /**
     * Excepción personalizada para la gestión de errores de productos.
     * Centraliza los fallos de lógica de negocio para ser capturados por el GlobalExceptionHandler.
     */
    @Getter
    public class ProductNotFoundException extends BaseBusinessException {
        public ProductNotFoundException(String message, BusinessErrorType type) {
            super(message, type);
        }

        /**
         * Define los tipos de errores específicos de productos.
         */
    //    public enum Type {
    //        NOT_FOUND,          // Recurso no encontrado (404)
    //        BAD_REQUEST,        // Error en los datos enviados por el cliente (400)
    //        CONFLICT,           // Conflicto de datos, como nombres duplicados (409)
     //       UNPROCESSABLE,      // 422: Regla de negocio rota (ej. stock insuficiente)
     //       UNAUTHORIZED,       // 401: No tiene permiso para esta acción.
     //       INTERNAL_SERVER,    // Error inesperado en el sistema (500)
        }

    //    private final Type type;

        /**
         * Constructor principal de la excepción.
         * @param message Descripción detallada del error.
         * @param type Categoría del error según el enumerado Type.
         */
   //     public ProductNotFoundException(String message, Type type) {
     //     this.type = type;
       // }
   // }