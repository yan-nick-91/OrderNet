package com.yann.customerservice.application;

import com.yann.customerservice.application.dto.*;
import com.yann.customerservice.application.mapper.CustomerMapper;
import com.yann.customerservice.application.mapper.ProductMapper;
import com.yann.customerservice.domain.Customer;
import com.yann.customerservice.domain.Product;
import com.yann.customerservice.domain.exceptions.CustomerNotFoundException;
import com.yann.customerservice.domain.utils.CreateIDFactory;
import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.infrastructure.events.InventoryClientRPC;
import com.yann.customerservice.infrastructure.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CustomerServiceImpl implements CustomerService {
    private final CreateIDFactory<CustomerID> customerIDFactory;
    private final CustomerRepository customerRepository;
    private final InventoryClientRPC inventoryClientRPC;

    public CustomerServiceImpl(CreateIDFactory<CustomerID> customerIDFactory,
                               CustomerRepository customerRepository, InventoryClientRPC inventoryClientRPC) {
        this.customerIDFactory = customerIDFactory;
        this.customerRepository = customerRepository;
        this.inventoryClientRPC = inventoryClientRPC;
    }

    @Override
    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO) {
        CustomerID customerID = customerIDFactory.create();
        Customer customer = CustomerMapper.toCustomer(customerID, customerRequestDTO);
        customerRepository.save(customer);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerMapper::toCustomerResponseDTO).toList();
    }

    @Override
    public CustomerResponseDTO findCustomerById(String id) {
        CustomerID customerID = customerIDFactory.set(id);
        Customer customer = customerRepository.findById(customerID)
                                              .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    public CustomerResponseDTO addProductToCustomer(String id, CustomerProductRequestDTO productRequestDTO) {
        CustomerID customerID = customerIDFactory.set(id);
        Customer customer = customerRepository.findById(customerID)
                                              .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        ProductCustomerResponseDTO productCustomerResponseDTO = inventoryClientRPC.requestProduct(productRequestDTO);
        Product product = ProductMapper.toProduct(productCustomerResponseDTO);
        customer.addProducts(product, productRequestDTO.quantity());
        customerRepository.save(customer);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public List<ProductCustomerResponseDTO> getCustomersProductsList(String customerId) {
        CustomerID customerID = customerIDFactory.set(customerId);
        Customer customer = customerRepository.findById(customerID)
                                              .orElseThrow(() ->
                                                      new CustomerNotFoundException(
                                                              "Customer not found or invalid ID"));

        return customer.getProductsRelations()
                       .stream()
                       .map(p -> ProductMapper.toProductCustomerResponseDTO(p.getProduct()))
                       .toList();
    }
}
