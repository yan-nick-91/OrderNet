package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.vo.CustomerID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Customer")
public class Customer {
    @Id
    private CustomerID customerID;
    private String firstname;
    private String lastname;
    private String email;
    private Address address;

    public Customer() {
    }

    public Customer(CustomerID customerID, String firstname,
                    String lastname, String email, Address address) {
        this.customerID = customerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
}
