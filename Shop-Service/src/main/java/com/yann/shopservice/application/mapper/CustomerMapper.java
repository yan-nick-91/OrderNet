package com.yann.shopservice.application.mapper;

import com.yann.shopservice.application.dto.*;
import com.yann.shopservice.domain.Address;
import com.yann.shopservice.domain.Cart;
import com.yann.shopservice.domain.Customer;
import com.yann.shopservice.domain.vo.CustomerID;
import com.yann.shopservice.domain.vo.Email;
import com.yann.shopservice.domain.vo.StreetNumber;

public class CustomerMapper {
    public static Customer toCustomer(CustomerID customerID, CustomerRequestDTO customerRequestDTO) {
        Address address = new Address(
                customerRequestDTO.address().zipcode(),
                customerRequestDTO.address().streetName(),
                new StreetNumber(customerRequestDTO.address().streetNumber()),
                customerRequestDTO.address().city(),
                customerRequestDTO.address().country());

        return new Customer(customerID, customerRequestDTO.firstname(),
                customerRequestDTO.lastname(),
                new Email(customerRequestDTO.email()),
                address
        );
    }

    public static CustomerDetailDTO toCustomerResponseDTO(Customer customer) {
        AddressDTO address = toAddressDTO(customer.getAddress());
        CartDTO cart = toCartDTO(customer.getCart());
        return new CustomerDetailDTO(customer.getCustomerID().value(), customer.getFirstname(),
                customer.getLastname(), customer.getEmail().value(),
                address, cart);
    }

    public static CustomerDetailDTO toCustomerResponseDTO(Customer customer, Cart cart) {
        AddressDTO address = toAddressDTO(customer.getAddress());
        CartDTO newCart = toCartDTO(cart);
        return new CustomerDetailDTO(customer.getCustomerID().value(), customer.getFirstname(),
                customer.getLastname(), customer.getEmail().value(),
                address, newCart);
    }

    public static CustomerOrderDTO toCustomerOrderDTO(Customer customer) {
        AddressDTO address = toAddressDTO(customer.getAddress());
        CartDTO cart = toCartDTO(customer.getCart());
        return new CustomerOrderDTO(customer.getFirstname(),
                customer.getLastname(), customer.getEmail().value(),
                address, cart);
    }

    // Helpers
    private static AddressDTO toAddressDTO(Address address) {
        return new AddressDTO(address.getZipcode(), address.getStreetName(),
                address.getStreetNumber().value(), address.getCity(), address.getCountry());
    }

    private static CartDTO toCartDTO(Cart cart) {
        return new CartDTO(cart.getCartID().value(), cart.getProducts(), cart.getTotalPrice());
    }
}
