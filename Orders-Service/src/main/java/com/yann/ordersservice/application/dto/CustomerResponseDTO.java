package com.yann.ordersservice.application.dto;

public record CustomerResponseDTO(
        String customerID,
        String firstname,
        String lastname,
        String email,
        AddressDTO address,
        CartDTO cart
) {
}
