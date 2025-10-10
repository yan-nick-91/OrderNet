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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class GetAllProductIntegrationTest {
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

    @BeforeEach
    void cleanDatabase() {
        productsRepository.deleteAll();
    }

    @Test
    @DisplayName("Should Return Empty List Of Products")
    void shouldReturnEmptyListOfProducts() throws Exception {
        mockMvc.perform(get("/api/inventories"))
               .andExpect(status().isOk())
               .andExpect(content().json("[]"));
    }

    @Test
    void shouldReturnAllFoundProducts() throws Exception {
        ProductID productIDOne = productIDFactory.set("PROD-ID-20250926-123e4567-e89b-12d3-a456-426614174000");
        ProductID productIDTwo = productIDFactory.set("PROD-ID-20250926-123e4567-e89b-12d3-a456-426614174111");

        Product savedProductOne = new Product(
                productIDOne,
                new ProductName("Test product"),
                new ProductPrice(19.99),
                10,
                new MaxQuantity(50)
        );
        productsRepository.save(savedProductOne);

        Product savedProductTwo = new Product(
                productIDTwo,
                new ProductName("Test product Deluxe"),
                new ProductPrice(29.99),
                20,
                new MaxQuantity(100)
        );
        productsRepository.save(savedProductTwo);

        mockMvc.perform(get("/api/inventories"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].productID").value(productIDOne.value()))
               .andExpect(jsonPath("$[0].name").value("Test product"))
               .andExpect(jsonPath("$[0].price").value(19.99))
               .andExpect(jsonPath("$[0].availableQuantity").value(10))
               .andExpect(jsonPath("$[0].maxQuantity").value(50))
               .andExpect(jsonPath("$[1].productID").value(productIDTwo.value()))
               .andExpect(jsonPath("$[1].name").value("Test product Deluxe"))
               .andExpect(jsonPath("$[1].price").value(29.99))
               .andExpect(jsonPath("$[1].availableQuantity").value(20))
               .andExpect(jsonPath("$[1].maxQuantity").value(100));
    }
}
