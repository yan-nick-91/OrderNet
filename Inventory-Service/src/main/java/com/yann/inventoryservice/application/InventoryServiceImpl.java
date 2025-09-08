package com.yann.inventoryservice.application;

import com.yann.inventoryservice.application.dto.*;
import com.yann.inventoryservice.application.mapper.OrderMapper;
import com.yann.inventoryservice.application.mapper.ProductMapper;
import com.yann.inventoryservice.domain.Order;
import com.yann.inventoryservice.domain.Product;
import com.yann.inventoryservice.domain.exception.OrderNotFoundException;
import com.yann.inventoryservice.domain.exception.ProductNotFoundException;
import com.yann.inventoryservice.domain.utils.CreateIDFactory;
import com.yann.inventoryservice.domain.utils.IDFactory;
import com.yann.inventoryservice.domain.vo.OrderID;
import com.yann.inventoryservice.domain.vo.ProductID;
import com.yann.inventoryservice.infrastructure.repository.OrderRepository;
import com.yann.inventoryservice.infrastructure.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class InventoryServiceImpl implements InventoryService {
    private final ProductsRepository productsRepository;
    private final OrderRepository orderRepository;
    private final CreateIDFactory<ProductID> productIDFactory;
    private final IDFactory<OrderID> orderIDFactory;

    public InventoryServiceImpl(ProductsRepository productsRepository, OrderRepository orderRepository,
                                CreateIDFactory<ProductID> productIDFactory, IDFactory<OrderID> orderIDFactory) {
        this.productsRepository = productsRepository;
        this.orderRepository = orderRepository;
        this.productIDFactory = productIDFactory;
        this.orderIDFactory = orderIDFactory;
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
    public void receivingOrder(OrderToInventoryRequestDTO orderToInventoryRequestDTO) {
        OrderID orderID = orderIDFactory.set(orderToInventoryRequestDTO.OrderID());
        ProductID productID = productIDFactory.set(orderToInventoryRequestDTO.ProductID());
        Order order = OrderMapper.toProductOrder(orderID, productID, orderToInventoryRequestDTO);
        orderRepository.save(order);
    }

    @Override
    public List<ProductOrderResponseDTO> getAllProductOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderMapper::toProductOrderResponse).toList();
    }

    @Override
    public ProductCustomerResponseDTO sendPickedUpProductToCustomer(String orderIDAsString) {
        return null;
    }

    @Override
    public StockResponseDTO getStockPercentageByProductId(String productId) {
        Product product = checkProductIdForProduct(productId);
        String percentage = product.checkAvailability();
        return new StockResponseDTO(product.getProductID(), product.getName(),
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
        // product cannot be deleted if there is still an order for this
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
