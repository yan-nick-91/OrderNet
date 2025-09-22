package com.yann.ordersservice.domain.exceptions;

public class InternalOrderStateErrorException extends RuntimeException {
    public InternalOrderStateErrorException(String message) {
        super(message);
    }
}
