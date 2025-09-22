package com.yann.inventoryservice.application;

import com.yann.inventoryservice.application.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(String productIDAsString);

    ProductResponseDTO getProductByName(String productName);

    ProductCustomerResponseDTO getProductForCustomerByName(String productName);

    void receivingOrder(OrderToInventoryRequestDTO orderToInventoryRequestDTO);

    List<ProductOrderResponseDTO> getAllProductOrders();

    ProductCustomerResponseDTO sendPickedUpProductToCustomer(String orderIDAsString);

    StockResponseDTO getStockPercentageByProductId(String productIDAsString);

    List<StockResponseDTO> getAllStockPercentage();

    StockResponseDTO increaseStockQuantityOfProduct(
            String productIDAsString, StockAdjustmentRequestDTO stockAdjustmentRequestDTO);

    StockResponseDTO decreaseStockQuantityOfProduct(
            String productIDAsString, StockAdjustmentRequestDTO stockAdjustmentRequestDTO);

    StockUpdateResponseDTO updateProductGeneral(
            String productIDAsString, StockUpdateRequestDTO stockUpdateRequestDTO);

    StockUpdateResponseDTO updateStockMaxQuantityOfProduct(
            String productIDAsString, ProductQuantityDTO productRequestDTO);
;
    void removeProduct(String productIDAsString);
}
