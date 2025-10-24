package com.yann.ordersservice.domain.exceptions;

public class InternalPaymentErrorException extends RuntimeException {
    public InternalPaymentErrorException(String message) {
        super(message);
    }
}
