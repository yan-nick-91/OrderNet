package com.yann.inventoryservice.application.dto;

public record StockUpdateRequestDTO(
        String name,
        double price,
        int maxQuantity
) {
}
