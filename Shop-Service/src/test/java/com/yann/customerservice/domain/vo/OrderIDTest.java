package com.yann.customerservice.domain.vo;

import com.yann.customerservice.domain.utils.IDFactory;
import com.yann.customerservice.domain.utils.OrderIDFactory;
import org.junit.jupiter.api.BeforeEach;

class OrderIDTest extends AbstractValueObjectTest<OrderID> {
    private IDFactory<OrderID> orderIDFactory;

    @BeforeEach
    void setUp() {
        this.orderIDFactory = new OrderIDFactory();
    }

    @Override
    protected OrderID createValidInstanceOne() {
        return orderIDFactory.set("ORD-ID-20250926-123e4567-e89b-12d3-a456-434214179434");
    }

    @Override
    protected OrderID createValidInstanceTwo() {
        return orderIDFactory.set("ORD-ID-20250926-123e4567-e89b-12d3-a456-434214179434");
    }

    @Override
    protected OrderID differentValidInstance() {
        return orderIDFactory.set("ORD-ID-20250926-123e4567-e89b-12d3-a456-434214179435");
    }
}