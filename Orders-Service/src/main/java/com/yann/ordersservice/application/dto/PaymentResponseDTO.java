package com.yann.ordersservice.application.dto;

public record PaymentResponseDTO(
        String orderID,
        String orderDate,
        CustomerResponseDTO customer
) {
}
