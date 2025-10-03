package com.yann.inventoryservice.presentation;

import com.yann.inventoryservice.domain.Product;
import com.yann.inventoryservice.domain.utils.IDFactory;
import com.yann.inventoryservice.domain.utils.ProductIDFactory;
import com.yann.inventoryservice.domain.vo.MaxQuantity;
import com.yann.inventoryservice.domain.vo.ProductID;
import com.yann.inventoryservice.domain.vo.ProductName;
import com.yann.inventoryservice.domain.vo.ProductPrice;
import com.yann.inventoryservice.infrastructure.repository.ProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class GetProductByIDIntegrationTest {
    private IDFactory<ProductID> productIDFactory = new ProductIDFactory();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductsRepository productsRepository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    private Product savedProduct;

    @BeforeEach
    void setUp() {
        productsRepository.deleteAll();
        ProductID productID = productIDFactory.set("PROD-ID-20250926-123e4567-e89b-12d3-a456-426614174000");

        savedProduct = new Product(
                productID,
                new ProductName("Test product"),
                new ProductPrice(19.99),
                10,
                new MaxQuantity(50)
        );
        savedProduct = productsRepository.save(savedProduct);
    }

    @Test
    void shouldReturnProductByID() throws Exception {
        String productIDAsString = "PROD-ID-20250926-123e4567-e89b-12d3-a456-426614174000";

        mockMvc.perform(get("/api/inventories/" + productIDAsString)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.productID").value(savedProduct.getProductID().value()))
               .andExpect(jsonPath("$.name").value("Test product"))
               .andExpect(jsonPath("$.price").value(19.99))
               .andExpect(jsonPath("$.availableQuantity").value(10));
    }

    @Test
    void shouldReturn404WhenProductDoesNotExist() throws Exception {
        String nonExistingProductIDAsString = "PROD-ID-20250926-123e4567-e89b-12d3-a456-426614174022";
        mockMvc.perform(get("/api/inventories/" + nonExistingProductIDAsString)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Product not found"));
    }

    @Test
    void shouldReturn400WhenProductIDIsInvalid() throws Exception {
        String nonExistingProductIDAsString = "PROT-ID-20250926-123e4567-e89b-12d3-a456-426614174022";
        mockMvc.perform(get("/api/inventories/" + nonExistingProductIDAsString)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }
}
