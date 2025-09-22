package com.yann.inventoryservice.application.dto;

public record ProductOrderResponseDTO(
        String OrderID,
        String ProductID,
        int quantity,
        CustomerDTO customerDTO
) {
}
