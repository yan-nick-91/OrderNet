package com.yann.ordersservice.infrastructure.repository;

import com.yann.ordersservice.domain.Order;
import com.yann.ordersservice.domain.vo.OrderID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, OrderID>, CustomOrderRepository {
}
