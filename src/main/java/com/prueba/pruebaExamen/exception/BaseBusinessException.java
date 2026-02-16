package com.prueba.pruebaExamen.exception;

import lombok.Getter;

@Getter
public abstract class BaseBusinessException extends RuntimeException {

    private final BusinessErrorType type;

    public BaseBusinessException(String message, BusinessErrorType type) {
        super(message);
        this.type = type;
    }
}