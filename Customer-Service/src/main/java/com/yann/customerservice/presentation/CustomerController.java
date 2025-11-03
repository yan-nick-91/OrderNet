package com.yann.customerservice.presentation;

import com.yann.customerservice.application.CustomerService;
import com.yann.customerservice.application.dto.CustomerRequestDTO;
import com.yann.customerservice.domain.exceptions.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllCustomers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.findAllCustomers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{customer_id_as_string}")
    public ResponseEntity<Object> findCustomerByID(@PathVariable("customer_id_as_string") String customer_id_as_string) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.findCustomerById(customer_id_as_string));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
