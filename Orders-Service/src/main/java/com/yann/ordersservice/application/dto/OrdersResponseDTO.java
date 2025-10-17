package com.yann.ordersservice.application.dto;

import com.yann.ordersservice.domain.vo.OrderID;
import com.yann.ordersservice.domain.vo.Sequence;

import java.util.Date;

public record OrdersResponseDTO(
        OrderID orderID,
        Sequence sequence,
        Date date,
        CustomerResponseDTO customerResponseDTO
) {
}
