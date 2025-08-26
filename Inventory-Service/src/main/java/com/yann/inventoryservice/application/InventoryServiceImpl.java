package com.yann.inventoryservice.application;

import com.yann.inventoryservice.application.dto.*;
import com.yann.inventoryservice.application.mapper.ProductMapper;
import com.yann.inventoryservice.domain.Product;
import com.yann.inventoryservice.domain.exception.ProductNotFoundException;
import com.yann.inventoryservice.domain.utils.CreateIDFactory;
import com.yann.inventoryservice.domain.vo.ProductID;
import com.yann.inventoryservice.infrastructure.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class InventoryServiceImpl implements InventoryService {
    private final ProductsRepository productsRepository;
    private final CreateIDFactory<ProductID> productIDFactory;

    public InventoryServiceImpl(ProductsRepository productsRepository, CreateIDFactory<ProductID> productIDFactory) {
        this.productsRepository = productsRepository;
        this.productIDFactory = productIDFactory;
    }

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        Product product = ProductMapper.toProduct(productRequestDTO);
        List<Product> products = productsRepository.findAll();
        product.checkIfProductIsAlreadyInitialized(products);
        productsRepository.save(product);
        return ProductMapper.toProductRequestDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productsRepository.findAll();
        return products.stream().map(ProductMapper::toProductRequestDTO).toList();
    }

    @Override
    public ProductResponseDTO getProductById(String productId) {
        Product product = checkProductIdForProduct(productId);
        return ProductMapper.toProductRequestDTO(product);
    }

    @Override
    public ProductResponseDTO getProductByName(String productName) {
        Product product = checkProductNameForProduct(productName);
        return ProductMapper.toProductRequestDTO(product);
    }

    @Override
    public ProductCustomerResponseDTO getProductForCustomerByName(String productName) {
        Product product = checkProductNameForProduct(productName);
        return ProductMapper.toProductCustomerResponseDTO(product);
    }

    @Override
    public StockResponseDTO getStockPercentageByProductId(String productId) {
        Product product = checkProductIdForProduct(productId);
        String percentage = product.checkAvailability();
        return new StockResponseDTO(product.getId(), product.getName(),
                product.getAvailableQuantity(), percentage);
    }

    @Override
    public List<StockResponseDTO> getAllStockPercentage() {
        List<Product> products = productsRepository.findAll();
        return products.stream().map(ProductMapper::toStockResponseDTO).toList();
    }

    @Override
    public StockResponseDTO increaseStockWithProducts(
            String productId, StockAdjustmentRequestDTO stockAdjustmentRequestDTO) {
        Product product = checkProductIdForProduct(productId);
        product.increaseQuantity(stockAdjustmentRequestDTO.quantity());
        return ProductMapper.toStockResponseDTO(product);
    }

    @Override
    public StockResponseDTO decreaseStockWithProducts(
            String productId, StockAdjustmentRequestDTO stockAdjustmentRequestDTO) {
        Product product = checkProductIdForProduct(productId);
        product.decreaseQuantity(stockAdjustmentRequestDTO.quantity());
        return ProductMapper.toStockResponseDTO(product);
    }

    @Override
    public StockUpdateResponseDTO updateProduct(String productId, StockUpdateRequestDTO stockUpdateRequestDTO) {
        Product product = checkProductIdForProduct(productId);
        Product updatedProduct = ProductMapper.toUpdateProduct(product, stockUpdateRequestDTO);
        product.updateMaxQuantity(stockUpdateRequestDTO.maxQuantity());
        productsRepository.save(product);
        return ProductMapper.toStockUpdateResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = checkProductIdForProduct(productId);
        productsRepository.delete(product);
    }

    // Helpers
    private Product checkProductIdForProduct(String productId) {
        ProductID productID = productIDFactory.set(productId);
        return productsRepository.findById(productID)
                                 .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Product checkProductNameForProduct(String productName) {
        return productsRepository.findByName(productName)
                                 .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
