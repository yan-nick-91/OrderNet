package com.yann.customerservice.domain;

import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.domain.vo.Email;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Customer")
public class Customer {
    @Id
    private CustomerID customerID;
    private String firstname;
    private String lastname;
    private Email email;

    @Relationship(type = "HAS_CART", direction = Relationship.Direction.OUTGOING)
    private Cart cart;

    @Relationship(type = "RESIDENT_AT", direction = Relationship.Direction.OUTGOING)
    private Address address;

    public Customer() {
    }

    public Customer(CustomerID customerID, String firstname, String lastname, Email email, Address address) {
        this.customerID = customerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.cart = new Cart();
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

    public void setAddress(Address address) {
        this.address = address;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
