package com.yann.customerservice.infrastructure.repository;

import com.yann.customerservice.domain.Product;
import com.yann.customerservice.domain.vo.ProductID;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ProductRepository extends Neo4jRepository<Product, ProductID> {
}
