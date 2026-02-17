package com.prueba.pruebaExamen.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

/**
 * Gestor global de excepciones para centralizar la respuesta de errores de la API.
 * Intercepta excepciones de negocio y las transforma en respuestas HTTP estandarizadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones basadas en BaseBusinessException.
     * Determina el código de estado HTTP según el tipo de error de negocio.
     */
    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<?> handleBusinessException(BaseBusinessException ex) {
        HttpStatus status = switch (ex.getType()) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case CONFLICT, EMAIL_IN_USE -> HttpStatus.CONFLICT;
            case UNPROCESSABLE -> HttpStatus.UNPROCESSABLE_ENTITY;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case NO_CONTENT -> HttpStatus.NO_CONTENT;
            case INTERNAL_SERVER -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.BAD_REQUEST;
        };

        if (status == HttpStatus.NO_CONTENT) {
            return ResponseEntity.status(status).build();
        }

        return ResponseEntity.status(status)
                .body(Map.of(
                        "status", status.value(),
                        "error", status.getReasonPhrase(),
                        "message", ex.getMessage()
                ));
    }


    /**
     * Captura errores de validación disparados por la anotación @Valid en los controladores.
     * Extrae el primer mensaje de error definido en el DTO (Bean Validation).
     */
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException ex) {

        // Obtiene el mensaje personalizado configurado en las anotaciones de validación (@NotBlank, @Email, etc.)
        // SOLO OPTIENE UN ERROR
       //  String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        // 1. Extraemos TODOS los errores de forma dinámica
        List<String> errors = ex.getBindingResult()
                .getFieldErrors() // Buscamos errores por campo
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "error", "Validation Error",
                        "message", errors
                ));
    }
}