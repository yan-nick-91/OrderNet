package com.yann.shopservice.application;

import com.yann.shopservice.application.dto.*;
import com.yann.shopservice.application.mapper.CustomerMapper;
import com.yann.shopservice.application.mapper.ProductMapper;
import com.yann.shopservice.domain.*;
import com.yann.shopservice.domain.exceptions.CustomerAlreadyExistsException;
import com.yann.shopservice.domain.exceptions.CustomerNotFoundException;
import com.yann.shopservice.domain.utils.CreateIDFactory;
import com.yann.shopservice.domain.vo.CartID;
import com.yann.shopservice.domain.vo.CustomerID;
import com.yann.shopservice.domain.vo.Email;
import com.yann.shopservice.infrastructure.cache.ProductCaching;
import com.yann.shopservice.infrastructure.repository.ProductRepository;
import com.yann.shopservice.infrastructure.repository.CustomerRepository;
import com.yann.shopservice.infrastructure.rpc.OrderClientRPC;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ShopServiceImpl implements ShopService {
    private final CreateIDFactory<CustomerID> customerIDFactory;
    private final CreateIDFactory<CartID> cartIDFactory;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ProductCaching productCaching;
    private final OrderClientRPC orderClientRPC;

    public ShopServiceImpl(CreateIDFactory<CustomerID> customerIDFactory, CreateIDFactory<CartID> cartIDFactory,
                           CustomerRepository customerRepository, ProductRepository productRepository,
                           ProductCaching productCaching, OrderClientRPC orderClientRPC) {
        this.customerIDFactory = customerIDFactory;
        this.cartIDFactory = cartIDFactory;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.productCaching = productCaching;
        this.orderClientRPC = orderClientRPC;
    }

    @Override
    public CustomerDetailDTO addCustomer(CustomerRequestDTO customerRequestDTO) {
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
    public List<CustomerDetailDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerMapper::toCustomerResponseDTO).toList();
    }

    @Override
    public CustomerDetailDTO findCustomerById(String customerIDAsString) {
        Customer customer = findCustomerByIDOrThrow(customerIDAsString);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public CustomerDetailDTO initializeProductToCart(
            String customerIDAsString, CustomerProductRequestDTO productRequestDTO) {
        Customer customer = findCustomerByIDOrThrow(customerIDAsString);

        Cart cart = customer.getCart();
        CustomerCartValidator customerCartValidator = new CustomerCartValidator();
        customerCartValidator.checkIfCartHasProduct(customer, cart, productRequestDTO.name());

        // If a product is not added to the cart, proceed to send an RPC request to inventory-service
        // to get the product, following to add the response to the cart
        ProductCustomerResponseDTO productCustomerResponseDTO =
                productCaching.getCachedProductByName(productRequestDTO.name());

        Product product = ProductMapper.toProduct(productCustomerResponseDTO);
        customer.getCart().addNewProductToCart(product, productRequestDTO.quantity());

        CartPriceCalculator cartPriceCalculator = new CartPriceCalculator();
        double totalPrice = cartPriceCalculator.calculateTotalPriceInCart(customer.getCart());
        customer.getCart().setTotalPrice(totalPrice);

        customerRepository.save(customer);
        return CustomerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public List<ProductCustomerResponseDTO> requestForCatalog() {
        return productCaching.getCachedProducts();
    }

    @Override
    public CustomerDetailDTO updateProductQuantityInCart(
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

        // Remove product from db when quantity equals zero
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

        CustomerOrderDTO customerOrderDTO = CustomerMapper.toCustomerOrderDTO(customer);
        return orderClientRPC.sendOrderToOrdersService(customer.getCustomerID(), customerOrderDTO);
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
