package com.yann.inventoryservice.application.dto;

import com.yann.inventoryservice.domain.vo.ProductID;

public record ProductResponseDTO(
        ProductID productID,
        String name,
        double price,
        int availableQuantity,
        int maxQuantity
) {
}
