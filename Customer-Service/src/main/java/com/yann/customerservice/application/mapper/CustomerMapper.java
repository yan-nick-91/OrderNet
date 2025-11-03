package com.yann.customerservice.application.mapper;

import com.yann.customerservice.application.dto.AddressDTO;
import com.yann.customerservice.application.dto.CustomerRequestDTO;
import com.yann.customerservice.application.dto.CustomerResponseDTO;
import com.yann.customerservice.domain.Address;
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

    public static Customer toUpdatingCustomer(Customer customer) {
        Address address = new Address(
            customer.getAddress().getZipcode(),
                customer.getAddress().getStreetName(),
                customer.getAddress().getStreetNumber(),
                customer.getAddress().getCity(),
                customer.getAddress().getCountry());

        return new Customer(customer.getCustomerID(), customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                address
        );
    }

    public static CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
        AddressDTO address = toAddressDTO(customer.getAddress());
        return new CustomerResponseDTO(customer.getCustomerID().value(), customer.getFirstname(),
                customer.getLastname(), customer.getEmail().value(), address);
    }

    // Helpers
    private static AddressDTO toAddressDTO(Address address) {
        return new AddressDTO(address.getZipcode(), address.getStreetName(),
                address.getStreetNumber().value(), address.getCity(), address.getCountry());
    }
}
