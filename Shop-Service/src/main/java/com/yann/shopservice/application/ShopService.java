package com.yann.shopservice.application;

import com.yann.shopservice.application.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShopService {
    CustomerDetailDTO addCustomer(CustomerRequestDTO customerRequestDTO);

    List<CustomerDetailDTO> getAllCustomers();

    CustomerDetailDTO findCustomerById(String customerIDAsString);

    CustomerDetailDTO initializeProductToCart(
            String customerIDAsString, CustomerProductRequestDTO customerProductRequestDTO);

    List<ProductCustomerResponseDTO> requestForCatalog();

    CustomerDetailDTO updateProductQuantityInCart(
            String customerIDAsString, AdjustProductQuantityRequestDTO adjustProductQuantityRequestDTO);

    PaymentResponseDTO sendPaymentToOrders(String customerIDAsString, PaymentRequestDTO paymentRequestDTO);

    List<ProductCustomerResponseDTO> getCustomersProductsList(String customerIDAsString);

    void removeCustomer(String customerIDAsString);
}
