package com.yann.customerservices.application.dto;

import com.yann.customerservice.domain.Address;
import com.yann.customerservice.domain.ProductRelation;
import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.domain.vo.Email;

import java.util.Set;

public record CustomerResponseDTO(
        CustomerID customerID,
        String firstName,
        String lastName,
        Email email,
        Address address,
        Set<ProductRelation> products
) {
}
