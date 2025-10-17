package com.yann.inventoryservice.application.mapper;

import com.yann.inventoryservice.application.dto.AddressDTO;
import com.yann.inventoryservice.application.dto.CustomerDTO;
import com.yann.inventoryservice.application.dto.OrderToInventoryRequestDTO;
import com.yann.inventoryservice.application.dto.ProductOrderResponseDTO;
import com.yann.inventoryservice.domain.Address;
import com.yann.inventoryservice.domain.Customer;
import com.yann.inventoryservice.domain.Order;
import com.yann.inventoryservice.domain.ProductState;
import com.yann.inventoryservice.domain.vo.CustomerID;
import com.yann.inventoryservice.domain.vo.OrderID;
import com.yann.inventoryservice.domain.vo.ProductID;
import com.yann.inventoryservice.domain.vo.StreetNumber;

public class OrderMapper {
    public static Order toProductOrder(
            OrderID orderID, ProductID productID, OrderToInventoryRequestDTO orderToInventoryRequestDTO) {
        Customer customer = toCustomer((orderToInventoryRequestDTO.customerDTO()));
        return new Order(orderID, productID, orderToInventoryRequestDTO.quantity(),
                customer, ProductState.ORDER_RECEIVED);
    }

    public static ProductOrderResponseDTO toProductOrderResponse(Order order) {
        CustomerDTO customer = toCustomerDTO(order.getCustomer());
        return new ProductOrderResponseDTO(order.getOrderID().value(), order.getProductID().value(),
                order.getQuantity(), customer);
    }

    // Helpers
    private static Customer toCustomer(CustomerDTO customerDTO) {
        Address address = toAddress(customerDTO.address());
        return new Customer(new CustomerID(customerDTO.customerID()), customerDTO.firstname(),
                customerDTO.lastname(), customerDTO.email(), address);
    }

    private static CustomerDTO toCustomerDTO(Customer customer) {
        AddressDTO address = toAddressDTO(customer.getAddress());
        return new CustomerDTO(customer.getCustomerID().value(), customer.getFirstname(),
                customer.getLastname(), customer.getEmail(), address);
    }

    private static Address toAddress(AddressDTO addressDTO) {
        return new Address(addressDTO.zipcode(), addressDTO.streetName(),
                new StreetNumber(addressDTO.streetNumber()), addressDTO.city(),
                addressDTO.country());
    }

    private static AddressDTO toAddressDTO(Address address) {
        return new AddressDTO(address.getZipcode(), address.getStreetName(),
                address.getStreetNumber().value(), address.getCity(), address.getCountry());
    }
}
