package com.prueba.pruebaExamen.exception;

import lombok.Getter;

/**
 * Excepción personalizada para gestionar errores específicos de la lógica de negocio de usuarios.
 * Extiende de RuntimeException para permitir el manejo de excepciones no comprobadas.
 */
@Getter
public class UserException extends BaseBusinessException {
        public UserException(String message, BusinessErrorType type) {

            super(message, type);
        }
    }

/**
     * Categorías de error que permiten al GlobalExceptionHandler determinar
     * el código de estado HTTP adecuado.
     */
 //   public enum Type {
   //     NOT_FOUND,      // Para recursos inexistentes (404)
     //   BAD_REQUEST,    // Para errores de validación de cliente (400)
      //  EMAIL_IN_USE,   // Para conflictos de duplicidad (409)
      //  INTERNAL_SERVER,// Para errores inesperados del servidor (500)
      //  NO_CONTENT      // Para peticiones exitosas sin datos (204)
   // }

  //  private final Type type;

    /**
     * Constructor principal de la excepción.
     * @param message Descripción detallada del error.
     * @param type Categoría del error según el enumerado Type.
     */
//    public UserNotFoundException(String message, Type type) {
  //      super(message);
    //    this.type = type;
   // }
//}