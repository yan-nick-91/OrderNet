package com.yann.customerservices.infrastructure.events;

import com.yann.customerservice.application.dto.CustomerProductRequestDTO;
import com.yann.customerservice.application.dto.ProductCustomerResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface InventoryClientRPC {
    ProductCustomerResponseDTO requestProduct(CustomerProductRequestDTO productRequest);
}
