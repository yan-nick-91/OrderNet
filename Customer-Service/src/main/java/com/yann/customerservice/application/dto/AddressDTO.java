package com.yann.customerservice.application.dto;

public record AddressDTO(
        String zipcode,
        String streetName,
        String streetNumber,
        String city,
        String country
) {
}
