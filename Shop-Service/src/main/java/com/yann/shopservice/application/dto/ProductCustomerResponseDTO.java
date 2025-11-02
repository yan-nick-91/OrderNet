package com.yann.shopservice.application.dto;

import com.yann.shopservice.domain.vo.ProductID;

public record ProductCustomerResponseDTO(
        ProductID productID,
        String name,
        double price
) {
}
