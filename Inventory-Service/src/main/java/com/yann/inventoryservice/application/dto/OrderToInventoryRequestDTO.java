package com.yann.inventoryservice.application.dto;

public record OrderToInventoryRequestDTO(
        String OrderID,
        String ProductID,
        int quantity,
        CustomerDTO customerDTO
) {
}
