package com.yann.shopservice.application.dto;

import com.yann.shopservice.domain.ProductRelation;

import java.util.List;

public record CartDTO(
        String cartID,
        List<ProductRelation> products,
        double totalPrice
) {
}
