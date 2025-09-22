package com.yann.inventoryservice.presentation;

import com.yann.inventoryservice.application.InventoryService;
import com.yann.inventoryservice.application.dto.ProductRequestDTO;
import com.yann.inventoryservice.application.dto.StockUpdateRequestDTO;
import com.yann.inventoryservice.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<Object> addProductToInventory(@RequestBody ProductRequestDTO productRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.addProduct(productRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getAllProducts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getProductById(id));
        } catch (OutOfStockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalInventoryUpdateException | IllegalQuantityUpdateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<Object> getAvailableProducts(@PathVariable("id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getStockPercentageByProductId(id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OutOfStockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{productName}")
    public ResponseEntity<Object> getProductByProductName(@PathVariable("productName") String productName) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getProductByName(productName));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/customer/{productName}")
    public ResponseEntity<Object> getProductByName(@PathVariable("productName") String productName) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(inventoryService.getProductForCustomerByName(productName));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RequestedAmountUnavailableException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductById(@PathVariable("id") String id,
                                                    @RequestBody StockUpdateRequestDTO stockUpdateRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(inventoryService.updateProductGeneral(id, stockUpdateRequestDTO));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

