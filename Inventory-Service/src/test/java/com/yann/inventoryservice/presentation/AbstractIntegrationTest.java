//package com.yann.inventoryservice.presentation;
//
//import com.yann.inventoryservice.infrastructure.repository.ProductsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@SpringBootTest
//@Testcontainers
//@ActiveProfiles("test")
//@AutoConfigureMockMvc
//public class AbstractIntegrationTest {
//    @Autowired
//    protected MockMvc mockMvc;
//    @Autowired
//    protected ProductsRepository productsRepository;
//
//    @Container
//    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
//
//    @DynamicPropertySource
//    static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//    }
//}
