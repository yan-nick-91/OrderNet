package com.yann.shopservice.domain.exceptions;

public class IllegalProductRelationException extends RuntimeException {
    public IllegalProductRelationException(String message) {
        super(message);
    }
}
