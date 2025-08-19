package com.yann.ordersservice.application.dto;

import com.yann.ordersservice.domain.vo.OrderID;
import com.yann.ordersservice.domain.vo.Sequence;

import java.util.Date;
import java.util.List;

public record OrdersResponseDTO(
        OrderID orderID,
        Sequence sequence,
        Date date,
        List<ProductsResponseDTO> products,
        double totalPrice
) {
}
