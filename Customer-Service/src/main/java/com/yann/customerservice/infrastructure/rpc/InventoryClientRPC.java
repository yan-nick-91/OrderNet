package com.yann.customerservice.infrastructure.rpc;

import com.yann.customerservice.application.dto.ProductCustomerResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface InventoryClientRPC {
    ProductCustomerResponseDTO requestProduct(String productName);
}
