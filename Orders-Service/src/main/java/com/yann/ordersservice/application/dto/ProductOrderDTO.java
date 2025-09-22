package com.yann.ordersservice.application.dto;

public record ProductOrderDTO (
    String productID,
    String productName,
    int quantity
) {
}
