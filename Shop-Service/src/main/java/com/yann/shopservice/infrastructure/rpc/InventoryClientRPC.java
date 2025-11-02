package com.yann.shopservice.infrastructure.rpc;

import com.yann.shopservice.application.dto.ProductCustomerResponseDTO;

import java.util.List;

public interface InventoryClientRPC {
    ProductCustomerResponseDTO requestProduct(String productName);
    List<ProductCustomerResponseDTO> requestAllProducts();
}
