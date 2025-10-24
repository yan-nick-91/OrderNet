package com.yann.customerservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("tests")
class ShopServiceApplicationTests {
    @Value("${spring.neo4j.authentication.username}")
    private static String neo4jUsername;

    @Value("${spring.neo4j.authentication.password}")
    private static String neo4jPassword;

    @Container
    static Neo4jContainer<?> neo4j = new Neo4jContainer<>("neo4j:5.23")
            .withAdminPassword(neo4jPassword);

    @DynamicPropertySource
    static void setProps(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4j::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> neo4jUsername);
        registry.add("spring.neo4j.authentication.neo4jPassword", () -> neo4jUsername);
    }


    @Test
    void contextLoads() {
    }

}
