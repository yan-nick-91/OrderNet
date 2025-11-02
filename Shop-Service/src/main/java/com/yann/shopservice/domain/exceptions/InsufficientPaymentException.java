package com.yann.shopservice.domain.exceptions;

public class InsufficientPaymentException extends RuntimeException {
    public InsufficientPaymentException(String message) {
        super(message);
    }
}
