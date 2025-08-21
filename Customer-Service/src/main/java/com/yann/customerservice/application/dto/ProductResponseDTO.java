package com.yann.customerservice.application.dto;

import com.yann.customerservice.domain.vo.ProductID;

public record ProductResponseDTO(ProductID productID, String productName, double price) {
}
