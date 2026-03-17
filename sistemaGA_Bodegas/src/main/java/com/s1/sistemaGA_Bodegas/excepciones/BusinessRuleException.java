package com.s1.sistemaGA_Bodegas.excepciones;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
