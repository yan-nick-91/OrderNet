package com.yann.customerservice.application;

import com.yann.customerservice.application.dto.*;
import com.yann.customerservice.application.mapper.CustomerMapper;
import com.yann.customerservice.application.mapper.ProductMapper;
import com.yann.customerservice.domain.Cart;
import com.yann.customerservice.domain.Customer;
import com.yann.customerservice.domain.Product;
import com.yann.customerservice.domain.exceptions.CustomerNotFoundException;
import com.yann.customerservice.domain.utils.CreateIDFactory;
import com.yann.customerservice.domain.vo.CartID;
import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.infrastructure.events.InventoryClientRPC;
import com.yann.customerservice.infrastructure.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CustomerServiceImpl implements CustomerService {
    private final CreateIDFactory<CustomerID> customerIDFactory;
    private final CreateIDFactory<CartID> cartIDFactory;
    private final CustomerRepository customerRepository;
    private final InventoryClientRPC inventoryClientRPC;

    public CustomerServiceImpl(CreateIDFactory<CustomerID> customerIDFactory, CreateIDFactory<CartID> cartIDFactory,
                               CustomerRepository customerRepository, InventoryClientRPC inventoryClientRPC) {
        this.customerIDFactory = customerIDFactory;
        this.cartIDFactory = cartIDFactory;
        this.customerRepository = customerRepository;
        this.inventoryClientRPC = inventoryClientRPC;
    }

    @Override
    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO) {
        List<Customer> existingCustomers = customerRepository.findAll();
        existingCustomers.forEach(customer ->
                customer.checkIfCustomersEmailIsPersisted(existingCustomers, customerRequestDTO.email()));

        CustomerID customerID = customerIDFactory.create();
        Customer customer = CustomerMapper.toCustomer(customerID, customerRequestDTO);

        CartID cartID = cartIDFactory.create();
        Cart cart = new Cart(cartID);
        customer.setCart(cart);

        customerRepository.save(customer);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerMapper::toCustomerResponseDTO).toList();
    }

    @Override
    public CustomerResponseDTO findCustomerById(String customerIDAsString) {
        CustomerID customerID = customerIDFactory.set(customerIDAsString);
        Customer customer = customerRepository.findById(customerID)
                                              .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    public CustomerResponseDTO addProductToCustomer(String customerId, CustomerProductRequestDTO productRequestDTO) {
        CustomerID customerID = customerIDFactory.set(customerId);
        Customer customer = customerRepository.findById(customerID)
                                              .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        ProductCustomerResponseDTO productCustomerResponseDTO =
                inventoryClientRPC.requestProduct(productRequestDTO.name());

        Product product = ProductMapper.toProduct(productCustomerResponseDTO);
        customer.getCart().addNewProductToCart(product, productRequestDTO.quantity());

        customerRepository.save(customer);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public List<ProductCustomerResponseDTO> getCustomersProductsList(String customerIDAsString) {
        CustomerID customerID = customerIDFactory.set(customerIDAsString);
        Customer customer = customerRepository.findById(customerID)
                                              .orElseThrow(() ->
                                                      new CustomerNotFoundException(
                                                              "Customer not found or invalid ID"));

        return customer.getCart()
                       .getProducts()
                       .stream()
                       .map(p -> ProductMapper.toProductCustomerResponseDTO(p.getProduct()))
                       .toList();
    }
}
