package com.yann.inventoryservice.application.dto;

public record StockAdjustmentRequestDTO(String productID, int quantity) {
}
