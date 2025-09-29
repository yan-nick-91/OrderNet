package com.yann.inventoryservice.domain.vo;

import com.yann.inventoryservice.domain.utils.CustomerIDFactory;
import com.yann.inventoryservice.domain.utils.IDFactory;
import org.junit.jupiter.api.BeforeEach;

class CustomerIDTest extends AbstractValueObjectTest<CustomerID> {
    private IDFactory<CustomerID> customerIDFactory;

    @BeforeEach
    void setUp() {
        this.customerIDFactory = new CustomerIDFactory();
    }

    @Override
    protected CustomerID createValidInstanceOne() {
        return customerIDFactory.set("CUS-ID-20250926-123e4567-e89b-12d3-a456-426614174876");
    }

    @Override
    protected CustomerID createValidInstanceTwo() {
        return customerIDFactory.set("CUS-ID-20250926-123e4567-e89b-12d3-a456-426614174876");
    }

    @Override
    protected CustomerID differentValidInstance() {
        return customerIDFactory.set("CUS-ID-20250926-123e4567-e89b-12d3-a456-426614174878");
    }
}