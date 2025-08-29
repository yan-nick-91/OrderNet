package com.yann.customerservice.application;

import com.yann.customerservice.application.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO findCustomerById(String customerIDAsString);

    CustomerResponseDTO initializeProductToCart(
            String customerIDAsString, CustomerProductRequestDTO customerProductRequestDTO);

    CustomerResponseDTO adjustQuantityOfExistingProductInCart(
            String customerIDAsString, AdjustProductQuantityRequestDTO adjustProductQuantityRequestDTO);

    List<ProductCustomerResponseDTO> getCustomersProductsList(String customerIDAsString);

    void deleteCustomer(String customerIDAsString);
}
