package com.yann.customerservice.application.dto;

public record AdjustProductQuantityRequestDTO(
        String productName,
        String adjustmentType,
        int quantity
) {
}
