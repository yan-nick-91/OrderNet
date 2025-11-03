package com.yann.customerservice.infrastructure.repositories;

import com.yann.customerservice.domain.Customer;
import com.yann.customerservice.domain.vo.CustomerID;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CustomerRepository extends CassandraRepository<Customer, CustomerID> {
}
