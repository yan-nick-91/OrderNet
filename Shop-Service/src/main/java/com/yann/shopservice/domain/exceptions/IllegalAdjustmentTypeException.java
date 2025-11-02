package com.yann.shopservice.domain.exceptions;

public class IllegalAdjustmentTypeException extends RuntimeException {
    public IllegalAdjustmentTypeException(String message) {
        super(message);
    }
}
