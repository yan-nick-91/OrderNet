package com.yann.customerservice.domain.exceptions;

public class IllegalProductRelationException extends RuntimeException {
    public IllegalProductRelationException(String message) {
        super(message);
    }
}
