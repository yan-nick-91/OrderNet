package com.yann.inventoryservice.application.dto;

public record CustomerDTO(
        String customerID,
        String firstname,
        String lastname,
        String email,
        AddressDTO address
) {
}
