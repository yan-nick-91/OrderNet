package com.yann.shopservice.application.dto;

public record AdjustProductQuantityRequestDTO(
        String productName,
        String adjustmentType,
        int quantity
) {
}
