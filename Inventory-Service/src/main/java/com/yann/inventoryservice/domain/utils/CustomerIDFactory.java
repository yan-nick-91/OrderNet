package com.yann.inventoryservice.domain.utils;

import com.yann.inventoryservice.domain.vo.CustomerID;
import org.springframework.stereotype.Component;

@Component
public class CustomerIDFactory implements IDFactory<CustomerID> {
    @Override
    public CustomerID set(String id) {
        return new CustomerID(id);
    }
}
