package com.yann.customerservice.application.dto;

public record CustomerOrderDTO(
        String firstname,
        String lastname,
        String email,
        AddressDTO address,
        CartDTO cart
) {
}
