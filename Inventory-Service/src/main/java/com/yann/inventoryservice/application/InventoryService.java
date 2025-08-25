package com.yann.inventoryservice.application;

import com.yann.inventoryservice.application.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(String productId);

    ProductResponseDTO getProductByName(String productName);
    StockResponseDTO getStockPercentageByProductId(String productId);
    List<StockResponseDTO> getAllStockPercentage();
    StockResponseDTO increaseStockWithProducts(String productId, StockAdjustmentRequestDTO stockAdjustmentRequestDTO);
    StockResponseDTO decreaseStockWithProducts(String productId, StockAdjustmentRequestDTO stockAdjustmentRequestDTO);

    ProductCustomerResponseDTO getProductForCustomer(String name, ProductCustomerRequestDTO product);

    void deleteProduct(String productId);
}
