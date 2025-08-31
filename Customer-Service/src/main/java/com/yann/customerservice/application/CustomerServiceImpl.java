package com.yann.customerservice.application;

import com.yann.customerservice.application.dto.*;
import com.yann.customerservice.application.mapper.CustomerMapper;
import com.yann.customerservice.application.mapper.ProductMapper;
import com.yann.customerservice.domain.*;
import com.yann.customerservice.domain.exceptions.CustomerNotFoundException;
import com.yann.customerservice.domain.utils.CreateIDFactory;
import com.yann.customerservice.domain.vo.CartID;
import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.domain.vo.OrderID;
import com.yann.customerservice.infrastructure.events.CustomerEventPublisher;
import com.yann.customerservice.infrastructure.rpc.InventoryClientRPC;
import com.yann.customerservice.infrastructure.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CustomerServiceImpl implements CustomerService {
    private final CreateIDFactory<CustomerID> customerIDFactory;
    private final CreateIDFactory<CartID> cartIDFactory;
    private final CreateIDFactory<OrderID> orderIDFactory;
    private final CustomerRepository customerRepository;
    private final InventoryClientRPC inventoryClientRPC;
    private final CustomerEventPublisher customerEventPublisher;

    public CustomerServiceImpl(CreateIDFactory<CustomerID> customerIDFactory, CreateIDFactory<CartID> cartIDFactory, CreateIDFactory<OrderID> orderIDFactory,
                               CustomerRepository customerRepository, InventoryClientRPC inventoryClientRPC, CustomerEventPublisher customerEventPublisher) {
        this.customerIDFactory = customerIDFactory;
        this.cartIDFactory = cartIDFactory;
        this.orderIDFactory = orderIDFactory;
        this.customerRepository = customerRepository;
        this.inventoryClientRPC = inventoryClientRPC;
        this.customerEventPublisher = customerEventPublisher;
    }

    @Override
    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO) {
        List<Customer> existingCustomers = customerRepository.findAll();
        existingCustomers.forEach(customer ->
                customer.checkIfCustomersEmailIsPersisted(existingCustomers, customerRequestDTO.email()));

        CustomerID customerID = customerIDFactory.create();
        Customer customer = CustomerMapper.toCustomer(customerID, customerRequestDTO);

        CartID cartID = cartIDFactory.create();
        Cart cart = new Cart(cartID);
        customer.setCart(cart);

        customerRepository.save(customer);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerMapper::toCustomerResponseDTO).toList();
    }

    @Override
    public CustomerResponseDTO findCustomerById(String customerIDAsString) {
        Customer customer = checkForExistingCustomerID(customerIDAsString);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    public CustomerResponseDTO initializeProductToCart(
            String customerIDAsString, CustomerProductRequestDTO productRequestDTO) {
        Customer customer = checkForExistingCustomerID(customerIDAsString);

        Cart cart = customer.getCart();
        customer.checkIfProductIsNotInCustomerItsCart(customer, cart, productRequestDTO.name());

        // If a product is not added to cart, send an RPC request can proceed
        ProductCustomerResponseDTO productCustomerResponseDTO =
                inventoryClientRPC.requestProduct(productRequestDTO.name());

        Product product = ProductMapper.toProduct(productCustomerResponseDTO);
        customer.getCart().addNewProductToCart(product, productRequestDTO.quantity());

        CartPriceCalculator cartPriceCalculator = new CartPriceCalculator();
        double totalPrice = cartPriceCalculator.calculateTotalPriceInCart(customer.getCart());
        customer.getCart().setTotalPrice(totalPrice);

        customerRepository.save(customer);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO adjustQuantityOfExistingProductInCart(
            String customerIDAsString, AdjustProductQuantityRequestDTO adjustProductQuantityRequestDTO) {
        Customer customer = checkForExistingCustomerID(customerIDAsString);

        customer.getCart().adjustProductQuantity(
                adjustProductQuantityRequestDTO.productName(),
                adjustProductQuantityRequestDTO.adjustmentType(),
                adjustProductQuantityRequestDTO.quantity());

        CartPriceCalculator cartPriceCalculator = new CartPriceCalculator();
        double totalPrice = cartPriceCalculator.calculateTotalPriceInCart(customer.getCart());
        customer.getCart().setTotalPrice(totalPrice);

        customerRepository.save(customer);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public PaymentResponseDTO sendPaymentToOrders(String customerIDAsString, PaymentRequestDTO paymentRequestDTO) {
        Customer customer = checkForExistingCustomerID(customerIDAsString);

        // TODO
        // 1. Before sending an event message, it should be checked if the
        // payment matches the total price in the cart of customer
        Cart cart = customer.getCart();

        CartPaymentChecker cartPaymentChecker = new CartPaymentChecker();
        cartPaymentChecker.checkIfPaymentMathesCartTotalPrice(paymentRequestDTO.totalPrice(), cart);

        cart.markProductRelationTypeToPending();

        // 2. If payment matches the total price, an Order class should be instantiated where its ID will be mentioned.
        OrderID orderID =  orderIDFactory.create();
        Order order = CustomerMapper.toOrder(orderID, customer);


        // 3. A mapper should be created
        // with the Customer, Cart, ProductIDs (ID should be mentioned) and Order
        PaymentResponseDTO paymentResponseDTO = CustomerMapper.toPaymentResponseDTO(order);

        // 4. If mapper is done, this can be sent
        customerEventPublisher.publishCustomerEvent(paymentResponseDTO);


        // 5. If sending the message to RabbitMQ is successful, the user should be informed

        return paymentResponseDTO;
    }

    @Override
    public List<ProductCustomerResponseDTO> getCustomersProductsList(String customerIDAsString) {
        Customer customer = checkForExistingCustomerID(customerIDAsString);

        return customer.getCart()
                       .getProducts()
                       .stream()
                       .map(p -> ProductMapper.toProductCustomerResponseDTO(p.getProduct()))
                       .toList();
    }

    @Override
    public void deleteCustomer(String customerIDAsString) {
        CustomerID customerID = customerIDFactory.set(customerIDAsString);
        customerRepository.deleteById(customerID);
    }

    // Helpers
    private Customer checkForExistingCustomerID(String customerIDAsString) {
        CustomerID customerID = customerIDFactory.set(customerIDAsString);
        return customerRepository.findById(customerID)
                                 .orElseThrow(() ->
                                         new CustomerNotFoundException(
                                                 "Customer not found or invalid ID"));
    }
}
