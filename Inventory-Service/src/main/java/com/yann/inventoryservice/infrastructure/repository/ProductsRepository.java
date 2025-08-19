package com.yann.inventoryservice.infrastructure.repository;

import com.yann.inventoryservice.domain.Product;
import com.yann.inventoryservice.domain.vo.ProductID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductsRepository extends MongoRepository<Product, ProductID> {
}
