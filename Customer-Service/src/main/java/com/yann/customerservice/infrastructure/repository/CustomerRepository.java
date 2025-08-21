package com.yann.customerservice.infrastructure.repository;

import com.yann.customerservice.domain.Customer;
import com.yann.customerservice.domain.vo.CustomerID;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CustomerRepository extends Neo4jRepository<Customer, CustomerID> {
}
