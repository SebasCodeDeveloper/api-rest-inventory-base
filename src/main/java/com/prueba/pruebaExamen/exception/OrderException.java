package com.prueba.pruebaExamen.exception;


public class OrderException extends BaseBusinessException {

    public OrderException(String message, BusinessErrorType Type) {

        super(message,  Type);

    }
}
