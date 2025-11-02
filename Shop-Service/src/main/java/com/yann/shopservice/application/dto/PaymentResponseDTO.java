package com.yann.shopservice.application.dto;

public record PaymentResponseDTO(
        String orderID,
        String orderDate
) {
}
