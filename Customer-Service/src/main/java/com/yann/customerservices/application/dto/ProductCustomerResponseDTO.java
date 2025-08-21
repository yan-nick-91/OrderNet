package com.yann.customerservices.application.dto;

import com.yann.customerservice.domain.vo.ProductID;

public record ProductCustomerResponseDTO(ProductID productID, String name, double price) {
}
