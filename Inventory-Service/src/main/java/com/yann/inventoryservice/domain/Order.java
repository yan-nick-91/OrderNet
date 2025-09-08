package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.vo.OrderID;
import com.yann.inventoryservice.domain.vo.ProductID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Order")
public class Order {
    @Id
    OrderID orderID;
    ProductID productID;
    int quantity;
    Customer customer;
    ProductState productState;

    public Order() {
    }

    public Order(OrderID orderID, ProductID productID, int quantity, Customer customer, ProductState productState) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.customer = customer;
        this.productState = productState;
    }

    public OrderID getOrderID() {
        return orderID;
    }

    public ProductID getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ProductState getProductState() {
        return productState;
    }
}
