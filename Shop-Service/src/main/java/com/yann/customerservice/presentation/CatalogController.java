package com.yann.customerservice.presentation;

import com.yann.customerservice.application.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop/catalog")
public class CatalogController {
    private final ShopService shopService;

    public CatalogController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public ResponseEntity<Object> getCatalog() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(shopService.RequestForCatalog());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
