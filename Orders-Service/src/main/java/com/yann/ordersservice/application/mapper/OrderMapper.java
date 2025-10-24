package com.yann.ordersservice.application.mapper;

import com.yann.ordersservice.application.dto.*;
import com.yann.ordersservice.domain.*;
import com.yann.ordersservice.domain.vo.*;

import java.util.Date;
import java.util.List;

public class OrderMapper {
    public static Order toOrder(OrderID orderID, CustomerDetailDTO customerDetailDTO) {
        Customer customer = toCustomer(customerDetailDTO);

        return new Order(orderID, customer);
    }

    public static Order toOrder(OrderID orderID, String customerIDAsString, CustomerOrderDTO customerOrderDTO) {
        Customer customer = toCustomer(customerIDAsString, customerOrderDTO);

        return new Order(orderID, customer);
    }

    public static OrdersResponseDTO toOrdersResponseDTO(Order order) {
        CustomerDetailDTO customer = toCustomerResponseDTO(order.getCustomer());
        return new OrdersResponseDTO(order.getOrderID(), order.getSequence(),
                Date.from(order.getDate()), customer);
    }

    public static CustomerDetailDTO toCustomerResponseDTO(Customer customer) {
        AddressDTO address = toAddressDTO(customer.getAddress());
        CartDTO cart = toCartDTO(customer.getCart());
        return new CustomerDetailDTO(customer.getCustomerID().value(), customer.getFirstname(),
                customer.getLastname(), customer.getEmail().value(), address, cart);
    }

    public static OrderToInventoryDTO toOrderToInventoryDTO(Order order) {
        List<ProductOrderDTO> productOrders = toProductOrderDTOS(order.getCustomer().getCart());
        return new OrderToInventoryDTO(order.getOrderID().value(), productOrders);
    }

    // Helpers
    private static Customer toCustomer(CustomerDetailDTO customerDetailDTO) {
        Cart cart = toCart(customerDetailDTO.cart());
        Address address = toAddress(customerDetailDTO.address());
        CustomerID customerID = new CustomerID(customerDetailDTO.customerID());

        return new Customer(customerID, customerDetailDTO.firstname(),
                customerDetailDTO.lastname(),
                new Email(customerDetailDTO.email()),
                address, cart);
    }

    private static Customer toCustomer(String customerIDAsString, CustomerOrderDTO customerOrderDTO) {
        Cart cart = toCart(customerOrderDTO.cart());
        Address address = toAddress(customerOrderDTO.address());
        CustomerID customerID = new CustomerID(customerIDAsString);

        return new Customer(customerID, customerOrderDTO.firstname(),
                customerOrderDTO.lastname(),
                new Email(customerOrderDTO.email()),
                address, cart);
    }

    private static CustomerDTO toCustomerDTO(Customer customer) {
        AddressDTO address = toAddressDTO(customer.getAddress());
        return new CustomerDTO(customer.getCustomerID().value(), customer.getFirstname(),
                customer.getLastname(), customer.getEmail().value(), address);
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

    private static List<ProductOrderDTO> toProductOrderDTOS(Cart cart) {
        return cart.getProducts()
                   .stream()
                   .map(pr -> new ProductOrderDTO(
                           pr.getProduct().getProductID().value(),
                           pr.getProduct().getProductName(),
                           pr.getQuantity())).toList();

    }
}
