package com.yann.customerservice.presentation;

import com.yann.customerservice.application.CustomerService;
import com.yann.customerservice.application.dto.AdjustProductQuantityRequestDTO;
import com.yann.customerservice.application.dto.CustomerProductRequestDTO;
import com.yann.customerservice.application.dto.CustomerRequestDTO;
import com.yann.customerservice.application.dto.PaymentRequestDTO;
import com.yann.customerservice.domain.exceptions.*;
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

    @PostMapping("/{customerIDAsString}/products")
    public ResponseEntity<Object> addProductToCustomerByItsID(
            @PathVariable String customerIDAsString, @RequestBody CustomerProductRequestDTO customerProductRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    customerService.initializeProductToCart(customerIDAsString, customerProductRequestDTO));
        } catch (ProductAlreadyInitializedInCartException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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

    @PostMapping("/{customerIDAsString}/payment")
    public ResponseEntity<Object> sendPaymentToOrdersByCustomerID(
            @PathVariable String customerIDAsString, @RequestBody PaymentRequestDTO paymentRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    customerService.sendPaymentToOrders(customerIDAsString, paymentRequestDTO));
        } catch (InsufficientPaymentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
            return ResponseEntity.status(HttpStatus.OK).body(customerService.findCustomerById(customerIDAsString));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{customerIDAsString}/products")
    public ResponseEntity<Object> findProductsByCustomerID(@PathVariable String customerIDAsString) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(customerService.getCustomersProductsList(customerIDAsString));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{customerIDAsString}/products/quantity")
    public ResponseEntity<Object> updateProductQuantity(
            @PathVariable String customerIDAsString,
            @RequestBody AdjustProductQuantityRequestDTO adjustProductQuantityRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(customerService.updateProductQuantityInCart(
                                         customerIDAsString, adjustProductQuantityRequestDTO));
        } catch (IllegalProductQuantityException | IllegalAdjustmentTypeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (CustomerNotFoundException | ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{customerIDAsString}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable String customerIDAsString) {
        try {
            customerService.removeCustomer(customerIDAsString);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                 .body("Customer " + customerIDAsString + " deleted successfully");
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
