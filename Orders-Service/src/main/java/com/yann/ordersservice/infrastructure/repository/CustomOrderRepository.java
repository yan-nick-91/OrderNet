package com.yann.ordersservice.infrastructure.repository;

import com.yann.ordersservice.domain.Order;

public interface CustomOrderRepository {
    Order findOldestOrder();
    Order findNewestOrder();
}
