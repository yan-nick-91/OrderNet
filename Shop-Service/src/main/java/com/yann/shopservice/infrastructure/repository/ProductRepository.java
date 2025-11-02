package com.yann.shopservice.infrastructure.repository;

import com.yann.shopservice.domain.Product;
import com.yann.shopservice.domain.vo.ProductID;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ProductRepository extends Neo4jRepository<Product, ProductID> {
}
