package com.yann.ordersservice.domain;

import com.yann.ordersservice.domain.vo.OrderID;
import com.yann.ordersservice.domain.vo.Sequence;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    private OrderID id;
    private Sequence sequence;
    private Date date;
    private OrderState state = OrderState.NONE;

    private final List<Product> products = new ArrayList<>();
    private double totalPrice;

    public Order() {
    }

    public Order(OrderID id) {
        this.id = id;
        this.sequence = new Sequence();
        this.date = new Date();
        this.state = OrderState.RESERVED;
    }

    public void calculateTotalPrice(List<Product> products) {
        totalPrice = products.stream()
                             .mapToDouble(product -> product.getPrice() * product.getQuantity())
                             .sum();
    }



    public OrderID getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public Sequence getSequence() {
        return sequence;
    }

    public Date getDate() {
        return date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
