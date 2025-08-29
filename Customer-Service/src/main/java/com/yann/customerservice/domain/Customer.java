package com.yann.customerservice.domain;

import com.yann.customerservice.domain.exceptions.CustomerAlreadyExistsException;
import com.yann.customerservice.domain.exceptions.ProductAlreadyInitializedInCartException;
import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.domain.vo.Email;
import com.yann.customerservice.domain.vo.OrderID;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Node("Customer")
public class Customer {
    @Id
    private CustomerID id;
    private String firstname;
    private String lastname;
    private Email email;

    @Relationship(type = "HAS_CART", direction = Relationship.Direction.OUTGOING)
    private Cart cart;

    @Relationship(type = "RESIDENT_AT", direction = Relationship.Direction.OUTGOING)
    private Address address;

    private Set<OrderID> orderIDS = new HashSet<>();

    public Customer() {
    }

    public Customer(CustomerID id, String firstname, String lastname, Email email, Address address) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.cart = new Cart();
    }

    public void checkIfCustomersEmailIsPersisted(List<Customer> existingCustomers, String emailToCheck) {
        existingCustomers.stream()
                         .map(Customer::getEmail)
                         .map(Email::value)
                         .filter(emailValue -> emailValue.equals(emailToCheck))
                         .findAny()
                         .ifPresent(emailValue -> {
                             throw new CustomerAlreadyExistsException("Email already registered: " + emailToCheck);
                         });
    }

    public void checkIfProductIsNotInCustomerItsCart(
            Customer existingCustomer, Cart cart, String productName) {
        if (cart.getProducts().isEmpty()) {
            return;
        }

        existingCustomer.getCart()
                        .getProducts()
                        .stream()
                        .map(ProductRelation::getProduct)
                        .filter(product -> product.getProductName().equalsIgnoreCase(productName))
                        .findAny()
                        .ifPresent(product -> {
                            throw new ProductAlreadyInitializedInCartException(
                                    "Product " + product.getProductName() +
                                            " already in cart. Use increasing or decreasing to adjust the " +
                                            "product quantity.");
                        });
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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Set<OrderID> getOrderIDS() {
        return orderIDS;
    }

    public void setOrderIDS(Set<OrderID> orderIDS) {
        this.orderIDS = orderIDS;
    }
}
