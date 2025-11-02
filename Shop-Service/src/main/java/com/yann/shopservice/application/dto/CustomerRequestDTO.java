package com.yann.shopservice.application.dto;

public record CustomerRequestDTO(
        String firstname,
        String lastname,
        String email,
        AddressDTO address
) {
}
