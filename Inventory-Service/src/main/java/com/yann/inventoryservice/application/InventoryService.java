package com.yann.inventoryservice.application;

import com.yann.inventoryservice.application.dto.ProductRequestDTO;
import com.yann.inventoryservice.application.dto.ProductResponseDTO;
import com.yann.inventoryservice.application.dto.StockResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(String id);

    StockResponseDTO getStockPercentageByProductId(String productId);
    List<StockResponseDTO> getAllStockPercentage();
    StockResponseDTO increaseStockWithProducts(String productId);
    StockResponseDTO decreaseStockWithProducts(String productId);

    void deleteProduct(String id);
}
