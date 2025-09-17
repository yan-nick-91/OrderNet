package com.yann.customerservice.application;

import com.yann.customerservice.application.dto.*;
import com.yann.customerservice.application.mapper.CustomerMapper;
import com.yann.customerservice.application.mapper.ProductMapper;
import com.yann.customerservice.domain.*;
import com.yann.customerservice.domain.exceptions.CustomerAlreadyExistsException;
import com.yann.customerservice.domain.exceptions.CustomerNotFoundException;
import com.yann.customerservice.domain.utils.CreateIDFactory;
import com.yann.customerservice.domain.vo.CartID;
import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.domain.vo.Email;
import com.yann.customerservice.domain.vo.OrderID;
import com.yann.customerservice.infrastructure.events.CustomerEventPublisher;
import com.yann.customerservice.infrastructure.repository.ProductRepository;
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
    private final ProductRepository productRepository;
    private final InventoryClientRPC inventoryClientRPC;
    private final CustomerEventPublisher customerEventPublisher;

    public CustomerServiceImpl(CreateIDFactory<CustomerID> customerIDFactory, CreateIDFactory<CartID> cartIDFactory,
                               CreateIDFactory<OrderID> orderIDFactory, CustomerRepository customerRepository,
                               ProductRepository productRepository, InventoryClientRPC inventoryClientRPC,
                               CustomerEventPublisher customerEventPublisher) {
        this.customerIDFactory = customerIDFactory;
        this.cartIDFactory = cartIDFactory;
        this.orderIDFactory = orderIDFactory;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.inventoryClientRPC = inventoryClientRPC;
        this.customerEventPublisher = customerEventPublisher;
    }

    @Override
    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO) {
        Email email = new Email(customerRequestDTO.email());
        customerRepository.findByEmail(email)
                          .ifPresent(c -> {
                              throw new CustomerAlreadyExistsException(
                                      "Email already exists: " + customerRequestDTO.email());
                          });

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
        Customer customer = findCustomerByIDOrThrow(customerIDAsString);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    public CustomerResponseDTO initializeProductToCart(
            String customerIDAsString, CustomerProductRequestDTO productRequestDTO) {
        Customer customer = findCustomerByIDOrThrow(customerIDAsString);

        Cart cart = customer.getCart();
        CustomerCartValidator customerCartValidator = new CustomerCartValidator();
        customerCartValidator.checkIfCartHasProduct(customer, cart, productRequestDTO.name());

        // If a product is not added to the cart, proceed to send an RPC request to inventory-service
        // to get the product, following to add the response to the cart
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
    public CustomerResponseDTO updateProductQuantityInCart(
            String customerIDAsString, AdjustProductQuantityRequestDTO adjustProductQuantityRequestDTO) {
        Customer customer = findCustomerByIDOrThrow(customerIDAsString);
        Cart cart = customer.getCart();

        cart.adjustProductQuantity(
                adjustProductQuantityRequestDTO.productName(),
                adjustProductQuantityRequestDTO.adjustmentType(),
                adjustProductQuantityRequestDTO.quantity());

        CartPriceCalculator cartPriceCalculator = new CartPriceCalculator();
        double totalPrice = cartPriceCalculator.calculateTotalPriceInCart(customer.getCart());
        customer.getCart().setTotalPrice(totalPrice);

        cart.getProducts()
            .stream()
            .filter(pr -> pr.getQuantity() == 0)
            .map(pr -> pr.getProduct().getProductID())
            .forEach(productRepository::deleteById);

        customerRepository.save(customer);
        cart.removeZeroQuantityProducts();
        return CustomerMapper.toCustomerResponseDTO(customer, cart);
    }

    @Override
    public PaymentResponseDTO sendPaymentToOrders(String customerIDAsString, PaymentRequestDTO paymentRequestDTO) {
        Customer customer = findCustomerByIDOrThrow(customerIDAsString);
        Cart cart = customer.getCart();

        CartPaymentChecker cartPaymentChecker = new CartPaymentChecker();
        cartPaymentChecker.verifyPaymentWithTotalPrice(paymentRequestDTO.totalPrice(), cart);
        cart.markProductRelationTypeToPending();

        OrderID orderID = orderIDFactory.create();
        Order order = CustomerMapper.toOrder(orderID, customer);

        PaymentResponseDTO paymentResponseDTO = CustomerMapper.toPaymentResponseDTO(order);
        customerEventPublisher.publishCustomerEvent(paymentResponseDTO);
        return paymentResponseDTO;
    }


    @Override
    public List<ProductCustomerResponseDTO> getCustomersProductsList(String customerIDAsString) {
        Customer customer = findCustomerByIDOrThrow(customerIDAsString);

        return customer.getCart()
                       .getProducts()
                       .stream()
                       .map(p -> ProductMapper.toProductCustomerResponseDTO(p.getProduct()))
                       .toList();
    }

    @Override
    public void removeCustomer(String customerIDAsString) {
        Customer customer = findCustomerByIDOrThrow(customerIDAsString);
        customerRepository.deleteById(customer.getCustomerID());
    }

    // Helpers
    private Customer findCustomerByIDOrThrow(String customerIDAsString) {
        CustomerID customerID = customerIDFactory.set(customerIDAsString);
        return customerRepository.findById(customerID)
                                 .orElseThrow(() ->
                                         new CustomerNotFoundException(
                                                 "Customer not found or invalid ID"));
    }
}
