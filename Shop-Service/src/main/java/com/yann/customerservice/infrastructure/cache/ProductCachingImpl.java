package com.yann.customerservice.infrastructure.cache;

import com.yann.customerservice.application.dto.ProductCustomerResponseDTO;
import com.yann.customerservice.infrastructure.rpc.InventoryClientRPC;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class ProductCachingImpl implements ProductCaching {
    private static final Logger LOG = LoggerFactory.getLogger(ProductCachingImpl.class);
    private List<ProductCustomerResponseDTO> cachedProducts = new ArrayList<>();
    private final InventoryClientRPC inventoryClient;

    public ProductCachingImpl(InventoryClientRPC inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    @PostConstruct
    private void initializeCache() {
        LOG.info("Initializing cache on application startup");
        refreshCacheForAllProducts();
    }

    @Scheduled(fixedRateString = "${cache.refresh.interval}") // 5 min in milliseconds
    private void scheduledCacheRefresh() {
        LOG.info("Cache refresh started");
        refreshCacheForAllProducts();
    }

    public List<ProductCustomerResponseDTO> getCachedProducts() {
        if (cachedProducts.isEmpty()) {
            LOG.info("Requesting products form inventory");
            refreshCacheForAllProducts();
            LOG.info("Process refreshing cache completed");
        }
        return cachedProducts;
    }

    public ProductCustomerResponseDTO getCachedProductByName(String productName) {
        return cachedProducts.stream()
                             .filter(productCustomerResponseDTO ->
                                     productCustomerResponseDTO.name().equals(productName))
                             .findFirst()
                             .orElseGet(() -> {
                                 ProductCustomerResponseDTO product =
                                         inventoryClient.requestProduct(productName);
                                 cachedProducts.add(product);
                                 return product;
                             });
    }

    private void refreshCacheForAllProducts() {
        try {
            cachedProducts = inventoryClient.requestAllProducts();
        } catch (Exception e) {
            LOG.error("Error while refreshing cache for all products: {}", e.getMessage(), e);
        }
    }
}
