package com.yann.ordersservice.domain;

import com.yann.ordersservice.domain.vo.OrderID;
import com.yann.ordersservice.domain.vo.Sequence;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.format.DateTimeParseException;

@Document(collection = "orders")
public class Order {
    @Id
    private OrderID orderID;
    private Sequence sequence;
    private Instant date;
    private OrderState state;

    private Customer customer;

    public Order() {
    }

    public Order(OrderID orderID, String orderDateAsString, Customer customer) {
        this.orderID = orderID;
        this.sequence = new Sequence();
        this.date = parseDate(orderDateAsString);
        this.customer = customer;
        this.state = orderStateSetToPaid(customer);
    }

    private Instant parseDate(String orderDateAsString) {
        try {
            return Instant.parse(orderDateAsString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + orderDateAsString, e);
        }
    }

    private OrderState orderStateSetToPaid(Customer customer) {
        OrderStateValidator orderStateValidator = new OrderStateValidator();
        orderStateValidator.orderStateIsPaid(this.orderID, customer);
        return OrderState.PAID;
    }

    public OrderID getOrderID() {
        return orderID;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public Instant getDate() {
        return date;
    }

    public OrderState getState() {
        return state;
    }

    public Customer getCustomer() {
        return customer;
    }
}
