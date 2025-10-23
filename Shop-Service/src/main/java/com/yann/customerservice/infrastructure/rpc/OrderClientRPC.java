package com.yann.customerservice.infrastructure.rpc;

import com.yann.customerservice.application.dto.CustomerOrderDTO;
import com.yann.customerservice.application.dto.PaymentResponseDTO;
import com.yann.customerservice.domain.vo.CustomerID;

public interface OrderClientRPC {
    PaymentResponseDTO sendOrderToOrdersService(CustomerID customerID, CustomerOrderDTO CustomerOrderDTO);
}
