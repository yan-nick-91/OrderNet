package com.yann.inventoryservice.application.dto;

public record ProductRequestDTO(
        String name,
        double price,
        int quantity,
        int maxQuantity
) {
}
