package com.yann.customerservice.infrastructure.repository;

import com.yann.customerservice.domain.Customer;
import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.domain.vo.Email;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface CustomerRepository extends Neo4jRepository<Customer, CustomerID> {
    Optional<Customer> findByEmail(Email email);
}
