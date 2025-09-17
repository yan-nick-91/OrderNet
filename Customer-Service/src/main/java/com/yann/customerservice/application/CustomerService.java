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

    CustomerResponseDTO updateProductQuantityInCart(
            String customerIDAsString, AdjustProductQuantityRequestDTO adjustProductQuantityRequestDTO);

    PaymentResponseDTO sendPaymentToOrders(String customerIDAsString, PaymentRequestDTO paymentRequestDTO);

    List<ProductCustomerResponseDTO> getCustomersProductsList(String customerIDAsString);

    void removeCustomer(String customerIDAsString);
}
