package com.yann.shopservice.infrastructure.rpc;

import com.yann.shopservice.application.dto.CustomerOrderDTO;
import com.yann.shopservice.application.dto.PaymentResponseDTO;
import com.yann.shopservice.domain.vo.CustomerID;

public interface OrderClientRPC {
    PaymentResponseDTO sendOrderToOrdersService(CustomerID customerID, CustomerOrderDTO CustomerOrderDTO);
}
