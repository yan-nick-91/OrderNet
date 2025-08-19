package com.yann.inventoryservice.application.dto;

import com.yann.inventoryservice.domain.vo.ProductID;

public record StockResponseDTO(
        ProductID productID,
        String name,
        int availableQuantity,
        String stockInformation
) {
}
