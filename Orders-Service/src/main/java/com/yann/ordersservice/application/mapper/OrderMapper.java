package com.yann.ordersservice.application.mapper;

import com.yann.ordersservice.application.dto.AddressDTO;
import com.yann.ordersservice.application.dto.CartDTO;
import com.yann.ordersservice.application.dto.PaymentResponseDTO;
import com.yann.ordersservice.domain.Address;
import com.yann.ordersservice.domain.Cart;
import com.yann.ordersservice.domain.Customer;
import com.yann.ordersservice.domain.Order;
import com.yann.ordersservice.domain.vo.*;


public class OrderMapper {
    public static Order toOrder(OrderID orderID, PaymentResponseDTO paymentResponseDTO) {
        Customer customer = toCustomer(paymentResponseDTO);
        return new Order(orderID, paymentResponseDTO.orderDate(), customer);
    }

    // Helpers
    private static Customer toCustomer(PaymentResponseDTO paymentResponseDTO) {
        Cart cart = toCart(paymentResponseDTO.customer().cart());
        Address address = toAddress(paymentResponseDTO.customer().address());
        CustomerID customerID = new CustomerID(paymentResponseDTO.customer().customerID());

        return new Customer(
                customerID,
                paymentResponseDTO.customer().firstname(),
                paymentResponseDTO.customer().lastname(),
                new Email(paymentResponseDTO.customer().email()),
                address,
                cart);
    }

    private static Address toAddress(AddressDTO addressDTO) {
        return new Address(
                addressDTO.zipcode(), addressDTO.streetName(),
                new StreetNumber(addressDTO.streetNumber()),
                addressDTO.city(), addressDTO.country());
    }

    private static Cart toCart(CartDTO cartDTO) {
        CartID cartID = new CartID(cartDTO.cartID());
        return new Cart(cartID, cartDTO.products(), cartDTO.totalPrice());
    }
}
