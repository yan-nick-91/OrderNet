package com.yann.customerservice.infrastructure.rpc;

import com.yann.customerservice.application.dto.CustomerOrderDTO;
import com.yann.customerservice.application.dto.PaymentResponseDTO;
import com.yann.customerservice.domain.vo.CustomerID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class OrderClientRPCImpl implements OrderClientRPC {
    private final RestTemplate restTemplate;
    private final String orderServiceUrl;

    public OrderClientRPCImpl(
            RestTemplateBuilder builder,
            @Value("${orders.service.url}") String orderServiceUrl) {
        this.restTemplate = builder.build();
        this.orderServiceUrl = orderServiceUrl;
    }

    public PaymentResponseDTO sendOrderToOrdersService(CustomerID customerID, CustomerOrderDTO CustomerOrderDTO) {
        String url = String.format("%s/%s", orderServiceUrl, customerID);
        return restTemplate.postForObject(url, CustomerOrderDTO, PaymentResponseDTO.class);
    }
}
