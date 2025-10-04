package com.yann.inventoryservice.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yann.inventoryservice.application.InventoryService;
import com.yann.inventoryservice.application.dto.ProductRequestDTO;
import com.yann.inventoryservice.application.dto.ProductResponseDTO;
import com.yann.inventoryservice.application.dto.StockResponseDTO;
import com.yann.inventoryservice.domain.Product;
import com.yann.inventoryservice.domain.exception.ProductNotFoundException;
import com.yann.inventoryservice.domain.utils.IDFactory;
import com.yann.inventoryservice.domain.utils.ProductIDFactory;
import com.yann.inventoryservice.domain.vo.MaxQuantity;
import com.yann.inventoryservice.domain.vo.ProductID;
import com.yann.inventoryservice.domain.vo.ProductName;
import com.yann.inventoryservice.domain.vo.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {
    private IDFactory<ProductID> productIDFactory = new ProductIDFactory();

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should Add Product Successfully")
    void shouldAddProductSuccessfully() throws Exception {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO(
                "Laptop", 799.99, 80, 100
        );

        this.productIDFactory = new ProductIDFactory();

        ProductID productID = productIDFactory.set("PROD-ID-20250926-123e4567-e89b-12d3-a456-426614174000");
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(
                productID, productRequestDTO.name(), productRequestDTO.price(),
                80, new MaxQuantity(100));

        when(inventoryService.addProduct(productRequestDTO)).thenReturn(productResponseDTO);

        String requestJson = objectMapper.writeValueAsString(productRequestDTO);
        String responseJson = objectMapper.writeValueAsString(productResponseDTO);

        mockMvc.perform(post("/api/inventories")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(requestJson))
               .andExpect(status().isCreated())
               .andExpect(content().json(responseJson));
    }

    @Test
    @DisplayName("Should Return InternalServerError When Service Fails With Post AddProduct")
    void shouldReturnInternalServerErrorWhenServiceFailsWithPostAddProduct() throws Exception {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO(
                "Laptop", 799.99, 80, 100);

        when(inventoryService.addProduct(productRequestDTO)).thenThrow(new RuntimeException("Something went wrong"));

        String requestJson = objectMapper.writeValueAsString(productRequestDTO);

        mockMvc.perform(post("/api/inventories")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(requestJson))
               .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Should Return All Products")
    void shouldReturnAllProducts() throws Exception {
        when(inventoryService.getAllProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/inventories"))
               .andExpect(status().isOk())
               .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Should Return InternalServerError When Service Fails With Get AllProducts")
    void shouldReturnInternalServerErrorWhenServiceFailsWithGetAllProducts() throws Exception {
        when(inventoryService.getAllProducts()).thenThrow(new RuntimeException("Something went wrong"));

        mockMvc.perform(get("/api/inventories"))
               .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Should Return Found Product By Id")
    void shouldReturnFoundProductById() throws Exception {
        String productIDAsString = "PROD-ID-20250926-123e4567-e89b-12d3-a456-426614174000";
        ProductID productID = productIDFactory.set(productIDAsString);
        MaxQuantity maxQuantity = new MaxQuantity(100);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(
                productID, "Laptop", 799.99, 90, maxQuantity);

        when(inventoryService.getProductById(productIDAsString)).thenReturn(productResponseDTO);

        mockMvc.perform(get("/api/inventories/" + productIDAsString)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(productResponseDTO)));
    }

    @Test
    void shouldReturnNotFound() throws Exception {
        String productID = "PROD-ID-20250926-123e4567-e89b-12d3-a456-426614174000";

        when(inventoryService.getProductById(productID))
                .thenThrow(new ProductNotFoundException("Product not found"));

        mockMvc.perform(get("/api/inventories/" + productID)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Product not found"));
    }

    @Test
    void shouldReturnConflict() throws Exception {
        String productIDAsString = "PROT-ID-20250926-123e4567-e89b-12d3-a456-426614174000";

        when(inventoryService.getProductById(productIDAsString))
                .thenThrow(new IllegalArgumentException("Invalid ProductID format: " + productIDAsString));

        mockMvc.perform(get("/api/inventories/" + productIDAsString)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Invalid ProductID format: " + productIDAsString));
    }

    @Test
    @DisplayName("Should Return Found ProductByName")
    void shouldReturnFoundProductByName() throws Exception {
        String productName = "Laptop";
        String productIDAsString = "PROD-ID-20250926-123e4567-e89b-12d3-a456-426614174000";
        ProductID productID = productIDFactory.set(productIDAsString);
        MaxQuantity maxQuantity = new MaxQuantity(100);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(
                productID, productName, 799.99, 90, maxQuantity);

        when(inventoryService.getProductByName(productName)).thenReturn(productResponseDTO);
        mockMvc.perform(get("/api/inventories/product/" + productName)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(productResponseDTO)));
    }

    @Test
    @DisplayName("Should Return ProductAvailability In Percentage")
    void shouldReturnProductAvailabilityInPercentage() throws Exception {
        String productIDAsString = "PROD-ID-20250926-123e4567-e89b-12d3-a456-426614174000";
        ProductID productID = productIDFactory.set(productIDAsString);
        MaxQuantity maxQuantity = new MaxQuantity(100);
        Product product = new Product(productID, new ProductName("Laptop"),
                new ProductPrice(799.99), 80, maxQuantity);

        StockResponseDTO stockResponseDTO = new StockResponseDTO(
                product.getProductID(), product.getName().value(),
                product.getAvailableQuantity(), "80.00%");

        when(inventoryService.getStockPercentageByProductId(productIDAsString)).thenReturn(stockResponseDTO);
        mockMvc.perform(get("/api/inventories/" + productID + "/available")
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(stockResponseDTO)));
    }
}