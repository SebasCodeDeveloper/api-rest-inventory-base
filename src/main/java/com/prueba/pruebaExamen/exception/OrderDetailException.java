package com.prueba.pruebaExamen.exception;


public class OrderDetailException extends BaseBusinessException {

    public OrderDetailException(String message, BusinessErrorType Type) {

        super(message, Type);
    }
}
