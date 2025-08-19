package com.yann.ordersservice.domain;

import com.yann.ordersservice.domain.vo.CustomerID;
import com.yann.ordersservice.domain.vo.Email;
import com.yann.ordersservice.domain.vo.OrderID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "customers")
public class Customer {
    @Id
    private CustomerID id;
    private String firstname;
    private String lastname;
    private Email email;
    private Address address;

    private List<OrderID> orderIDS;

    private Customer() {}

    public Customer(CustomerID id, String firstname,
                    String lastname, Email email,
                    Address address) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
    }

    public void addOrderID(OrderID orderID) {
        orderIDS.add(orderID);
    }

    public void removeOrderID(OrderID orderID) {
        orderIDS.remove(orderID);
    }

    public CustomerID getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public List<OrderID> getOrderIDS() {
        return orderIDS;
    }
}
