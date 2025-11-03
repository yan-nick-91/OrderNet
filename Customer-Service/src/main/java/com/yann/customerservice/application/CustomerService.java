package com.yann.customerservice.application;

import com.yann.customerservice.application.dto.CustomerRequestDTO;
import com.yann.customerservice.application.dto.CustomerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO);
    CustomerResponseDTO findCustomerById(String customerIDAsString);
    List<CustomerResponseDTO> findAllCustomers();
    CustomerResponseDTO updateCustomer(String customerIDAsString, CustomerRequestDTO customerRequestDTO);
    void deleteCustomer(String customerIDAsString);
}
