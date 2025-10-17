package com.yann.inventoryservice.infrastructure.repository;

import com.yann.inventoryservice.domain.Order;
import com.yann.inventoryservice.domain.vo.OrderID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, OrderID> {
}
