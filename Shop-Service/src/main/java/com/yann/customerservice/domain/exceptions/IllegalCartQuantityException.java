package com.yann.customerservice.domain.exceptions;

public class IllegalCartQuantityException extends RuntimeException {
    public IllegalCartQuantityException(String message) {
        super(message);
    }
}
