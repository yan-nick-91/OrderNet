package com.yann.customerservice.infrastructure.rpc;

import com.yann.customerservice.application.dto.ProductCustomerResponseDTO;

import java.util.List;

public interface InventoryClientRPC {
    ProductCustomerResponseDTO requestProduct(String productName);
    List<ProductCustomerResponseDTO> requestAllProducts();
}
