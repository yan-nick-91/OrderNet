package com.yann.customerservice.application;

import com.yann.customerservice.application.dto.CustomerRequestDTO;
import com.yann.customerservice.application.dto.CustomerResponseDTO;
import com.yann.customerservice.application.mapper.CustomerMapper;
import com.yann.customerservice.domain.Customer;
import com.yann.customerservice.domain.exceptions.CustomerNotFoundException;
import com.yann.customerservice.domain.utils.CreateIDFactory;
import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.infrastructure.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CustomerServiceImpl implements CustomerService {
    private final CreateIDFactory<CustomerID> customerIDFactory;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CreateIDFactory<CustomerID> customerIDFactory, CustomerRepository customerRepository) {
        this.customerIDFactory = customerIDFactory;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        CustomerID customerID = customerIDFactory.create();
        Customer customer = CustomerMapper.toCustomer(customerID, customerRequestDTO);
        customerRepository.save(customer);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO findCustomerById(String customerIDAsString) {
        CustomerID customerID = customerIDFactory.set(customerIDAsString);
        Customer customer = customerRepository.findById(customerID)
                                              .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public List<CustomerResponseDTO> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerMapper::toCustomerResponseDTO).toList();
    }

    @Override
    public CustomerResponseDTO updateCustomer(String customerIDAsString, CustomerRequestDTO customerRequestDTO) {
        CustomerID customerID = customerIDFactory.set(customerIDAsString);
        Customer customer = customerRepository.findById(customerID)
                                              .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Customer customerToUpdate = CustomerMapper.toUpdatingCustomer(customer);
        customerRepository.save(customerToUpdate);
        return CustomerMapper.toCustomerResponseDTO(customerToUpdate);
    }

    @Override
    public void deleteCustomer(String customerIDAsString) {
        CustomerID customerID = customerIDFactory.set(customerIDAsString);
        customerRepository.findById(customerID)
                          .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerRepository.deleteById(customerID);
    }
}
