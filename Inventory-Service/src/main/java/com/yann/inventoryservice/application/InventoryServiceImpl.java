package com.yann.inventoryservice.application;

import com.yann.inventoryservice.application.dto.*;
import com.yann.inventoryservice.application.mapper.OrderMapper;
import com.yann.inventoryservice.application.mapper.ProductMapper;
import com.yann.inventoryservice.domain.Order;
import com.yann.inventoryservice.domain.Product;
import com.yann.inventoryservice.domain.ProductAvailability;
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
    private final ProductAvailability productAvailability;
    private final OrderRepository orderRepository;
    private final CreateIDFactory<ProductID> productIDFactory;
    private final IDFactory<OrderID> orderIDFactory;

    public InventoryServiceImpl(ProductsRepository productsRepository, ProductAvailability productAvailability,
                                OrderRepository orderRepository, CreateIDFactory<ProductID> productIDFactory,
                                IDFactory<OrderID> orderIDFactory) {
        this.productsRepository = productsRepository;
        this.productAvailability = productAvailability;
        this.orderRepository = orderRepository;
        this.productIDFactory = productIDFactory;
        this.orderIDFactory = orderIDFactory;
    }

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        Product product = ProductMapper.toProduct(productRequestDTO);
        List<Product> products = productsRepository.findAll();
        productAvailability.verifyIfProductIsInStock(product, products);
        productsRepository.save(product);
        return ProductMapper.toProductRequestDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productsRepository.findAll();
        return products.stream().map(ProductMapper::toProductRequestDTO).toList();
    }

    @Override
    public ProductResponseDTO getProductById(String productIDAsString) {
        Product product = findProductByItsIDAsStringOrThrow(productIDAsString);
        return ProductMapper.toProductRequestDTO(product);
    }

    @Override
    public ProductResponseDTO getProductByName(String productName) {
        Product product = findProductByItsNameOrThrow(productName);
        return ProductMapper.toProductRequestDTO(product);
    }

    @Override
    public ProductCustomerResponseDTO getProductForCustomerByName(String productName) {
        Product product = findProductByItsNameOrThrow(productName);
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
    public StockResponseDTO getStockPercentageByProductId(String productIDAsString) {
        Product product = findProductByItsIDAsStringOrThrow(productIDAsString);
        String percentage = productAvailability.displayAvailability(product);
        return new StockResponseDTO(product.getProductID(), product.getName().value(),
                product.getAvailableQuantity(), percentage);
    }

    @Override
    public List<StockResponseDTO> getAllStockPercentage() {
        List<Product> products = productsRepository.findAll();
        return products.stream().map(ProductMapper::toStockResponseDTO).toList();
    }

    @Override
    public StockResponseDTO increaseStockQuantityOfProduct(
            String productIDAsString, StockAdjustmentRequestDTO stockAdjustmentRequestDTO) {
        Product product = findProductByItsIDAsStringOrThrow(productIDAsString);
        product.increaseQuantity(stockAdjustmentRequestDTO.quantity());
        productsRepository.save(product);
        return ProductMapper.toStockResponseDTO(product);
    }

    @Override
    public StockResponseDTO decreaseStockQuantityOfProduct(
            String productIDAsString, StockAdjustmentRequestDTO stockAdjustmentRequestDTO) {
        Product product = findProductByItsIDAsStringOrThrow(productIDAsString);
        product.decreaseQuantity(stockAdjustmentRequestDTO.quantity());
        productsRepository.save(product);
        return ProductMapper.toStockResponseDTO(product);
    }

    @Override
    public StockUpdateResponseDTO updateProductGeneral(
            String productIDAsString, StockUpdateRequestDTO stockUpdateRequestDTO) {
        Product product = findProductByItsIDAsStringOrThrow(productIDAsString);
        Product updatedProduct = ProductMapper.toUpdateProduct(product, stockUpdateRequestDTO);
        product.updateMaxQuantity(stockUpdateRequestDTO.maxQuantity());
        productsRepository.save(product);
        return ProductMapper.toStockUpdateResponse(updatedProduct);
    }

    @Override
    public StockUpdateResponseDTO updateStockMaxQuantityOfProduct(
            String productIDAsString, ProductQuantityDTO productRequestDTO) {
        Product product = findProductByItsIDAsStringOrThrow(productIDAsString);
        product.updateMaxQuantity(productRequestDTO.quantity());
        productsRepository.save(product);
        return ProductMapper.toStockUpdateResponse(product);
    }

    @Override
    public void removeProduct(String productIDAsString) {
        Product product = findProductByItsIDAsStringOrThrow(productIDAsString);
        // product cannot be deleted if there is still an order for this
        productsRepository.delete(product);
    }

    // Helpers
    private Product findProductByItsIDAsStringOrThrow(String productIDAsString) {
        ProductID productID = productIDFactory.set(productIDAsString);
        return productsRepository.findById(productID)
                                 .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Product findProductByItsNameOrThrow(String productName) {
        return productsRepository.findByName(productName)
                                 .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
