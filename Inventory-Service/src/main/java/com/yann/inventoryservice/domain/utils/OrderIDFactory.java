package com.yann.inventoryservice.domain.utils;

import com.yann.inventoryservice.domain.vo.OrderID;
import org.springframework.stereotype.Component;

@Component
public class OrderIDFactory implements IDFactory<OrderID> {
    @Override
    public OrderID set(String id) {
        return new OrderID(id);
    }
}
