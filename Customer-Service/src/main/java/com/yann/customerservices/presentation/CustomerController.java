package com.yann.customerservices.presentation;

import com.yann.customerservice.application.CustomerService;
import com.yann.customerservice.application.dto.CustomerProductRequestDTO;
import com.yann.customerservice.application.dto.CustomerRequestDTO;
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
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(customerService.addCustomer(customerRequestDTO));
    }

    @PostMapping("/{id}/products")
    public ResponseEntity<Object> addProductToCustomerByItsID(
            @PathVariable String id, @RequestBody CustomerProductRequestDTO customerProductRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    customerService.addProductToCustomer(id, customerProductRequestDTO));
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
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findCustomerByID(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.findCustomerById(id));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
