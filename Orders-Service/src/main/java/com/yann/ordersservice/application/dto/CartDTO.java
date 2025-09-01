package com.yann.ordersservice.application.dto;

import com.yann.ordersservice.domain.ProductRelation;

import java.util.List;

public record CartDTO(
        String cartID,
        List<ProductRelation> products,
        double totalPrice
) {
}
