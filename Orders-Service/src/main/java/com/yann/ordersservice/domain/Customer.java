package com.yann.ordersservice.domain;

import com.yann.ordersservice.domain.vo.CustomerID;
import com.yann.ordersservice.domain.vo.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Customer {
    @Id
    private CustomerID customerID;
    private String firstname;
    private String lastname;
    private Email email;
    private Address address;
    private Cart cart;

    private Customer() {
    }

    public Customer(CustomerID customerID, String firstname,
                    String lastname, Email email,
                    Address address, Cart cart) {
        this.customerID = customerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.cart = cart;
    }

    public CustomerID getCustomerID() {
        return customerID;
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

    public Cart getCart() {
        return cart;
    }
}
