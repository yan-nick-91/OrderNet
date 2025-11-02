package com.yann.shopservice.domain.exceptions;

public class IllegalProductQuantityException extends RuntimeException {
    public IllegalProductQuantityException(String message) {
        super(message);
    }
}
