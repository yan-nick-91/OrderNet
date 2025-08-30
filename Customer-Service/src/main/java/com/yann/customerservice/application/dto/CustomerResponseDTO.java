package com.yann.customerservice.application.dto;

public record CustomerResponseDTO(
        String customerID,
        String firstName,
        String lastName,
        String email,
        AddressDTO address,
        CartDTO cart
) {
}
