package com.yann.ordersservice.infrastructure.repository;

import com.yann.ordersservice.domain.Customer;
import com.yann.ordersservice.domain.vo.CustomerID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, CustomerID> {
}
