package com.yann.customerservice.application.mapper;

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

    public static CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
        return new CustomerResponseDTO(customer.getId(), customer.getFirstname(),
                customer.getLastname(), customer.getEmail(),
                customer.getAddress(), customer.getProductsRelations());
    }
}
