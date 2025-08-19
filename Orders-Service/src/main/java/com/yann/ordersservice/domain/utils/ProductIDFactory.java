package com.yann.ordersservice.domain.utils;

import com.yann.ordersservice.domain.vo.ProductID;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductIDFactory implements CreateIDFactory<ProductID> {
    @Override
    public ProductID create() {
        return new ProductID(UUID.randomUUID().toString());
    }

    @Override
    public ProductID set(String productID) {
        return new ProductID(productID);
    }
}
