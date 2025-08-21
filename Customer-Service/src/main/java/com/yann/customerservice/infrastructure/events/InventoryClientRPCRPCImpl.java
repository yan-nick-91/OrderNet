package com.yann.customerservice.infrastructure.events;

import com.yann.customerservice.application.dto.CustomerProductRequestDTO;
import com.yann.customerservice.application.dto.ProductCustomerResponseDTO;
import com.yann.customerservice.domain.exceptions.InventoryServiceException;
import com.yann.customerservice.domain.exceptions.ProductUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
class InventoryClientRPCRPCImpl implements InventoryClientRPC {
    private final RestTemplate restTemplate;
    private final String inventoryServiceUrl;

    public InventoryClientRPCRPCImpl(RestTemplateBuilder builder,
                                     @Value("${inventory.service.url}") String inventoryServiceUrl) {
        this.restTemplate = builder.build();
        this.inventoryServiceUrl = inventoryServiceUrl;

    }

    @Override
    public ProductCustomerResponseDTO requestProduct(CustomerProductRequestDTO productRequest) {
        try {
            String url = String.format("%s/item/%s", inventoryServiceUrl, productRequest.name());
            ProductCustomerResponseDTO reply = restTemplate.getForObject(
                    url,
                    ProductCustomerResponseDTO.class);

            if (reply == null) {
                // http status 503
                throw new ProductUnavailableException("Inventory service unavailable, try again later");
            }
            return new ProductCustomerResponseDTO(
                    reply.productID(),
                    reply.name(),
                    reply.price());

        } catch (ProductUnavailableException |
                 HttpClientErrorException.NotFound |
                 HttpClientErrorException.Conflict e) {
            throw e;
        } catch (HttpClientErrorException e) {
            throw new InventoryServiceException("Inventory service error: " + e.getStatusCode());
        } catch (Exception e) {
            throw new InventoryServiceException("Unexpected error: " + e.getMessage());
        }
    }
}