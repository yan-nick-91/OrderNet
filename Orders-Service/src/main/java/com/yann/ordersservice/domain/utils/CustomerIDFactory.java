package com.yann.ordersservice.domain.utils;

import com.yann.ordersservice.domain.vo.CustomerID;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerIDFactory implements IDFactory<CustomerID> {
    @Override
    public CustomerID set(String id) {
        return new CustomerID(id);
    }
}
