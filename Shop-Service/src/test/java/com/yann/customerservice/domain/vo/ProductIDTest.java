package com.yann.customerservice.domain.vo;

import com.yann.customerservice.domain.utils.IDFactory;
import com.yann.customerservice.domain.utils.ProductIDFactory;
import org.junit.jupiter.api.BeforeEach;

class ProductIDTest extends AbstractValueObjectTest<ProductID> {
    private IDFactory<ProductID> productIDFactory;

    @BeforeEach
    void setUp() {
        this.productIDFactory = new ProductIDFactory();
    }

    @Override
    protected ProductID createValidInstanceOne() {
        return productIDFactory.set("PROD-ID-20250926-123e4567-e89b-12d3-a456-554216169454");
    }

    @Override
    protected ProductID createValidInstanceTwo() {
        return productIDFactory.set("PROD-ID-20250926-123e4567-e89b-12d3-a456-554216169454");
    }

    @Override
    protected ProductID differentValidInstance() {
        return productIDFactory.set("PROD-ID-20250926-123e4567-e89b-12d3-a456-554216169455");
    }
}