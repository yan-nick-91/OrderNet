package com.yann.customerservice.domain;

import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.domain.vo.Email;
import com.yann.customerservice.domain.vo.OrderID;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Customer")
public class Customer {
    @Id
    private CustomerID id;
    private String firstname;
    private String lastname;
    private Email email;

    @Relationship(type = "RESIDENT_AT")
    private Address address;

    @Relationship(type = "INTERACT_WITH")
    private Set<ProductRelation> productsRelations = new HashSet<>();

    private Set<OrderID> orderIDS = new HashSet<>();

    public Customer() {
    }

    public Customer(CustomerID id, String firstname, String lastname, Email email, Address address) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
    }

    public void addProduct(Product product, ProductRelationType type) {
        this.productsRelations.add(new ProductRelation(product, type));
    }

    public void removeProduct(Product product) {
        this.productsRelations.removeIf(relation -> relation.getProduct().equals(product));
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

    public Set<ProductRelation> getProductsRelations() {
        return productsRelations;
    }
}
