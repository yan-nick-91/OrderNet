package com.yann.customerservice.application.dto;

public record PaymentResponseDTO(
        String orderID,
        String orderDate,
        CustomerResponseDTO customer) {
}
