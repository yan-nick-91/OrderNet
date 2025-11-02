package com.yann.shopservice.infrastructure.repository;

import com.yann.shopservice.domain.Customer;
import com.yann.shopservice.domain.vo.CustomerID;
import com.yann.shopservice.domain.vo.Email;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface CustomerRepository extends Neo4jRepository<Customer, CustomerID> {
    Optional<Customer> findByEmail(Email email);
}
