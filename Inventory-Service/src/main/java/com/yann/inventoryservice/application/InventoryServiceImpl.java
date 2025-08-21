package com.yann.inventoryservice.application;

import com.yann.inventoryservice.application.dto.ProductCustomerResponseDTO;
import com.yann.inventoryservice.application.dto.ProductRequestDTO;
import com.yann.inventoryservice.application.dto.ProductResponseDTO;
import com.yann.inventoryservice.application.dto.StockResponseDTO;
import com.yann.inventoryservice.application.mapper.ProductMapper;
import com.yann.inventoryservice.domain.Product;
import com.yann.inventoryservice.domain.exception.ProductNotFoundException;
import com.yann.inventoryservice.domain.vo.ProductID;
import com.yann.inventoryservice.infrastructure.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class InventoryServiceImpl implements InventoryService {
    private final ProductsRepository productsRepository;

    public InventoryServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        Product product = ProductMapper.toProduct(productRequestDTO);
        List<Product> products = productsRepository.findAll();
        product.checkIfProductExists(products);

        productsRepository.save(product);
        return ProductMapper.toProductRequestDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productsRepository.findAll();
        return products.stream().map(ProductMapper::toProductRequestDTO).toList();
    }

    @Override
    public ProductResponseDTO getProductById(String id) {
        ProductID productID = new ProductID(id);
        Product product = productsRepository.findById(productID)
                                            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return ProductMapper.toProductRequestDTO(product);
    }

    @Override
    public ProductResponseDTO getProductByName(String productName) {
        Product product = productsRepository.findByName(productName)
                                            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return ProductMapper.toProductRequestDTO(product);
    }

    @Override
    public StockResponseDTO getStockPercentageByProductId(String productId) {
        ProductID productID = new ProductID(productId);
        Product product = productsRepository.findById(productID)
                                            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

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
    public StockResponseDTO increaseStockWithProducts(String productId) {
        return null;
    }

    @Override
    public StockResponseDTO decreaseStockWithProducts(String productId) {
        return null;
    }

    @Override
    public ProductCustomerResponseDTO getProductByNameForCustomer(String productName) {
        Product product = productsRepository.findByName(productName)
                                            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return ProductMapper.productCustomerResponseDTO(product);
    }

    @Override
    public void deleteProduct(String id) {

    }
}
