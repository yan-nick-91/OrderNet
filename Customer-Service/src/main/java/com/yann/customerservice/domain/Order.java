package com.yann.customerservice.domain;

import com.yann.customerservice.domain.vo.OrderID;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.Instant;

@Node("Order")
public class Order {
    @Id
    private OrderID orderID;
    private Customer customer;
    private Instant orderDateTimeStamp;

    public Order() {
    }

    public Order(OrderID orderID, Customer customer) {
        this.orderID = orderID;
        this.customer = customer;
        this.orderDateTimeStamp = Instant.now();

    }

    public OrderID getOrderID() {
        return orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Instant getOrderDate() {
        return orderDateTimeStamp;
    }
}
