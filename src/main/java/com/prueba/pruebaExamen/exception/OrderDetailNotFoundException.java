package com.prueba.pruebaExamen.exception;


public class OrderDetailNotFoundException extends BaseBusinessException {

    public OrderDetailNotFoundException(String message, BusinessErrorType Type) {

        super(message, Type);
    }
}
