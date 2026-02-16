package com.prueba.pruebaExamen.exception;


public class OrderNotFoundException extends BaseBusinessException {

    public OrderNotFoundException(String message, BusinessErrorType Type) {

        super(message,  Type);

    }
}
