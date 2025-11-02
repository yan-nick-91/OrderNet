package com.yann.shopservice.domain.exceptions;

public class IllegalIDValueException extends RuntimeException {
    public IllegalIDValueException(String message) {
        super(message);
    }
}
