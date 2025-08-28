package com.yann.customerservice.presentation;

import com.yann.customerservice.application.CustomerService;
import com.yann.customerservice.application.dto.CustomerProductRequestDTO;
import com.yann.customerservice.application.dto.CustomerRequestDTO;
import com.yann.customerservice.domain.exceptions.CustomerAlreadyExistsException;
import com.yann.customerservice.domain.exceptions.CustomerNotFoundException;
import com.yann.customerservice.domain.exceptions.ProductUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Object> addCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(customerService.addCustomer(customerRequestDTO));
        } catch (CustomerAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/{customerIdAsString}/products")
    public ResponseEntity<Object> addProductToCustomerByItsID(
            @PathVariable String customerIdAsString,
            @RequestBody CustomerProductRequestDTO customerProductRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    customerService.addProductToCustomer(customerIdAsString, customerProductRequestDTO));
        } catch (CustomerNotFoundException | HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ProductUnavailableException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (HttpClientErrorException.Conflict e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllCustomers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{customerIDAsString}")
    public ResponseEntity<Object> findCustomerByID(@PathVariable String customerIDAsString) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    customerService.findCustomerById(customerIDAsString));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{customerIDAsString}/products")
    public ResponseEntity<Object> findProductsByCustomerID(@PathVariable String customerIDAsString) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    customerService.getCustomersProductsList(customerIDAsString));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
