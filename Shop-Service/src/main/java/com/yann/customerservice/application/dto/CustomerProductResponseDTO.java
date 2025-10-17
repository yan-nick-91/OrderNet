package com.yann.customerservice.application.dto;

public record CustomerProductResponseDTO(
        String productID,
        String name,
        double price
) {
}
