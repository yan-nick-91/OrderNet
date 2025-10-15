package com.yann.customerservice.domain.vo;

import com.yann.customerservice.domain.utils.CreateIDFactory;
import com.yann.customerservice.domain.utils.CustomerIDFactory;
import org.junit.jupiter.api.BeforeEach;

class CustomerIDTest extends AbstractValueObjectTest<CustomerID> {
    private CreateIDFactory<CustomerID> createIDFactory;

    @BeforeEach
    void setUp() {
        this.createIDFactory = new CustomerIDFactory();
    }

    @Override
    protected CustomerID createValidInstanceOne() {
        return createIDFactory.set("CUS-ID-20250926-123e4567-e89b-12d3-a456-426614174385");
    }

    @Override
    protected CustomerID createValidInstanceTwo() {
        return createIDFactory.set("CUS-ID-20250926-123e4567-e89b-12d3-a456-426614174385");
    }

    @Override
    protected CustomerID differentValidInstance() {
        return createIDFactory.set("CUS-ID-20250926-123e4567-e89b-12d3-a456-426614174386");
    }
}