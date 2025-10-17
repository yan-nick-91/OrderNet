package com.yann.customerservice.domain.utils;

import com.yann.customerservice.domain.vo.ProductID;
import org.springframework.stereotype.Component;

@Component
public class ProductIDFactory implements IDFactory<ProductID> {
    @Override
    public ProductID set(String id) {
        return new ProductID(id);
    }
}
