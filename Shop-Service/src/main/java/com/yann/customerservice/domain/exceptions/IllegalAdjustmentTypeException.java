package com.yann.customerservice.domain.exceptions;

public class IllegalAdjustmentTypeException extends RuntimeException {
    public IllegalAdjustmentTypeException(String message) {
        super(message);
    }
}
