package com.yann.ordersservice.domain.utils;

import com.yann.ordersservice.domain.vo.OrderID;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderIDFactory implements CreateIDFactory<OrderID> {
    @Override
    public OrderID create() {
        return new OrderID(UUID.randomUUID().toString());
    }

    @Override
    public OrderID set(String id) {
        return new OrderID(id);
    }
}
