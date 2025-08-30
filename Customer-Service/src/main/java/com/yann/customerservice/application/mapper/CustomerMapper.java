package com.yann.customerservice.application.mapper;

import com.yann.customerservice.application.dto.AddressDTO;
import com.yann.customerservice.application.dto.CartDTO;
import com.yann.customerservice.application.dto.CustomerRequestDTO;
import com.yann.customerservice.application.dto.CustomerResponseDTO;
import com.yann.customerservice.domain.Address;
import com.yann.customerservice.domain.Cart;
import com.yann.customerservice.domain.Customer;
import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.domain.vo.Email;
import com.yann.customerservice.domain.vo.StreetNumber;

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

    public static CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
        AddressDTO address = toAddressDTO(customer.getAddress());
        CartDTO cart = toCartDTO(customer.getCart());
        return new CustomerResponseDTO(customer.getId().value(), customer.getFirstname(),
                customer.getLastname(), customer.getEmail().value(),
                address, cart);
    }

    public static CustomerResponseDTO toCustomerResponseDTO(Customer customer, Cart cart) {
        AddressDTO address = toAddressDTO(customer.getAddress());
        CartDTO newCart = toCartDTO(cart);
        return new CustomerResponseDTO(customer.getId().value(), customer.getFirstname(),
                customer.getLastname(), customer.getEmail().value(),
                address, newCart);
    }

    // helpers
    private static AddressDTO toAddressDTO(Address address) {
        return new AddressDTO(address.getZipcode(), address.getStreetName(),
                address.getStreetNumber().value(), address.getCity(), address.getCountry());
    }

    private static CartDTO toCartDTO(Cart cart) {
        return new CartDTO(cart.getCartID().value(), cart.getProducts(), cart.getTotalPrice());
    }
}
