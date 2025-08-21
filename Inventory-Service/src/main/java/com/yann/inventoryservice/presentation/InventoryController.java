package com.yann.inventoryservice.presentation;

import com.yann.inventoryservice.application.InventoryService;
import com.yann.inventoryservice.application.dto.ProductRequestDTO;
import com.yann.inventoryservice.domain.exception.IllegalInventoryUpdateException;
import com.yann.inventoryservice.domain.exception.IllegalQuantityUpdateException;
import com.yann.inventoryservice.domain.exception.OutOfStockException;
import com.yann.inventoryservice.domain.exception.ProductNotFoundException;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.addProduct(productRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getAllProducts());
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

    @GetMapping("/item/{name}")
    public ResponseEntity<Object> getProductByName(@PathVariable("name") String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(inventoryService.getProductByNameForCustomer(name));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OutOfStockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
