package com.yann.inventoryservice.application.dto;

public record OrderResponseDTO(
        String productID,
        String productName,
        int quantity
) {
}
