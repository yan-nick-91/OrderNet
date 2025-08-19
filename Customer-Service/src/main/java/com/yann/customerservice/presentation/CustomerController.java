package com.yann.customerservice.presentation;

import com.yann.customerservice.application.CustomerService;
import com.yann.customerservice.application.dto.CustomerRequestDTO;
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
    public ResponseEntity<Object> addCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(customerService.addCustomer(customerRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Object> getAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findCustomerByID(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.findCustomerById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
