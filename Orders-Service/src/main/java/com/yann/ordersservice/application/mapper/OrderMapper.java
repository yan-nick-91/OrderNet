package com.yann.ordersservice.application.mapper;

import com.yann.ordersservice.application.dto.*;
import com.yann.ordersservice.domain.*;
import com.yann.ordersservice.domain.vo.*;

import java.util.Date;

public class OrderMapper {
    public static Order toOrder(OrderID orderID, PaymentResponseDTO paymentResponseDTO) {
        Customer customer = toCustomer(paymentResponseDTO);
        return new Order(orderID, paymentResponseDTO.orderDate(), customer);
    }

    public static OrdersResponseDTO toOrdersResponseDTO(Order order) {
        CustomerResponseDTO customer = toCustomerResponseDTO(order.getCustomer());
        return new OrdersResponseDTO(order.getOrderID(), order.getSequence(),
                Date.from(order.getDate()), customer);
    }

    public static CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
        AddressDTO address = toAddressDTO(customer.getAddress());
        CartDTO cart = toCartDTO(customer.getCart());

        return new CustomerResponseDTO(customer.getCustomerID().value(), customer.getFirstname(),
                customer.getLastname(), customer.getEmail().value(), address, cart);
    }

    // Helpers
    private static Customer toCustomer(PaymentResponseDTO paymentResponseDTO) {
        Cart cart = toCart(paymentResponseDTO.customer().cart());
        Address address = toAddress(paymentResponseDTO.customer().address());
        CustomerID customerID = new CustomerID(paymentResponseDTO.customer().customerID());

        return new Customer(customerID, paymentResponseDTO.customer().firstname(),
                paymentResponseDTO.customer().lastname(),
                new Email(paymentResponseDTO.customer().email()),
                address, cart);
    }

    private static Address toAddress(AddressDTO addressDTO) {
        return new Address(addressDTO.zipcode(), addressDTO.streetName(),
                new StreetNumber(addressDTO.streetNumber()),
                addressDTO.city(), addressDTO.country());
    }

    private static AddressDTO toAddressDTO(Address address) {
        return new AddressDTO(address.getZipcode(), address.getStreetName(),
                address.getStreetNumber().value(), address.getCity(),
                address.getCountry());
    }

    private static Cart toCart(CartDTO cartDTO) {
        CartID cartID = new CartID(cartDTO.cartID());
        return new Cart(cartID, cartDTO.products(), cartDTO.totalPrice());
    }

    private static CartDTO toCartDTO(Cart cart) {
        return new CartDTO(cart.getCartID().value(),
                cart.getProducts(), cart.getTotalPrice());
    }
}
