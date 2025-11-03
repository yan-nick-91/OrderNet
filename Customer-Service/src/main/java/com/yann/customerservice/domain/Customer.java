package com.yann.customerservice.domain;

import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.domain.vo.Email;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("customers")
public class Customer {
    @PrimaryKey
    @Column("id")
    private CustomerID customerID;
    @Column("firstname")
    private String firstname;
    @Column("lastname")
    private String lastname;
    @Column("email")
    private Email email;
    @Column("address")
    private Address address;

    public Customer() {
    }

    public Customer(CustomerID customerID, String firstname, String lastname, Email email, Address address) {
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

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
