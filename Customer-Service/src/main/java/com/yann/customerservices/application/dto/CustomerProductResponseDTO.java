package com.yann.customerservices.application.dto;

public record CustomerProductResponseDTO(
        String ProductID,
        String name,
        double price
) {
}
