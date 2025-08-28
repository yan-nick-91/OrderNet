package com.yann.customerservice.application;

import com.yann.customerservice.application.dto.CustomerProductRequestDTO;
import com.yann.customerservice.application.dto.CustomerRequestDTO;
import com.yann.customerservice.application.dto.CustomerResponseDTO;
import com.yann.customerservice.application.dto.ProductCustomerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO findCustomerById(String customerIDAsString);

    CustomerResponseDTO addProductToCustomer(
            String customerIDAsString, CustomerProductRequestDTO customerProductRequestDTO);

    List<ProductCustomerResponseDTO> getCustomersProductsList(String customerIDAsString);
}
