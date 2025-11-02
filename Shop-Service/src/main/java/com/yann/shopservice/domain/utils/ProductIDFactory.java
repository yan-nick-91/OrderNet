package com.yann.shopservice.domain.utils;

import com.yann.shopservice.domain.vo.ProductID;
import org.springframework.stereotype.Component;

@Component
public class ProductIDFactory implements IDFactory<ProductID> {
    @Override
    public ProductID set(String id) {
        return new ProductID(id);
    }
}
