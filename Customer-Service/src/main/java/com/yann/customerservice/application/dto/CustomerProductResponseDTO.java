package com.yann.customerservice.application.dto;

public record CustomerProductResponseDTO(
        String ProductID,
        String name,
        double price
) {
}
