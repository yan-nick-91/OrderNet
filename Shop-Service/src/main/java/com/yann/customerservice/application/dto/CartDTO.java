package com.yann.customerservice.application.dto;

import com.yann.customerservice.domain.ProductRelation;

import java.util.List;

public record CartDTO(
        String cartID,
        List<ProductRelation> products,
        double totalPrice
) {
}
