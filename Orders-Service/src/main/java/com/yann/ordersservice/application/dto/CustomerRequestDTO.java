package com.yann.ordersservice.application.dto;

public record CustomerRequestDTO(
        String firstName,
        String lastName,
        String email,
        AddressDTO address
) {
}
