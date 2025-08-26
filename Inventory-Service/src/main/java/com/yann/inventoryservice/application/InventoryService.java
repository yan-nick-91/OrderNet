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

    ProductCustomerResponseDTO getProductForCustomerByName(String productName);

    StockResponseDTO getStockPercentageByProductId(String productId);

    List<StockResponseDTO> getAllStockPercentage();

    StockResponseDTO increaseStockWithProducts(String productId, StockAdjustmentRequestDTO stockAdjustmentRequestDTO);

    StockResponseDTO decreaseStockWithProducts(String productId, StockAdjustmentRequestDTO stockAdjustmentRequestDTO);

    StockUpdateResponseDTO updateProduct(String productId, StockUpdateRequestDTO stockUpdateRequestDTO);

    void deleteProduct(String productId);
}
