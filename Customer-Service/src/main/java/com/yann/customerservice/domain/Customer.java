package com.yann.customerservice.domain;

import com.yann.customerservice.domain.exceptions.IllegalProductQuantityException;
import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.domain.vo.Email;
import com.yann.customerservice.domain.vo.OrderID;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Node("Customer")
public class Customer {
    @Id
    private CustomerID id;
    private String firstname;
    private String lastname;
    private Email email;

    @Relationship(type = "RESIDENT_AT", direction = Relationship.Direction.OUTGOING)
    private Address address;

    @Relationship(type = "HAS_PRODUCT", direction = Relationship.Direction.OUTGOING)
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

    public void addProducts(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalProductQuantityException("Quantity must be greater than 0");
        }

        Collections.nCopies(quantity, product)
                   .forEach(p -> this.productsRelations.add(
                           new ProductRelation(product, ProductRelationType.ADD_TO_CART)));
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

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<ProductRelation> getProductsRelations() {
        return productsRelations;
    }

    public void setProductsRelations(Set<ProductRelation> productsRelations) {
        this.productsRelations = productsRelations;
    }

    public Set<OrderID> getOrderIDS() {
        return orderIDS;
    }

    public void setOrderIDS(Set<OrderID> orderIDS) {
        this.orderIDS = orderIDS;
    }
}
