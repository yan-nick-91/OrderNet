package com.yann.ordersservice.infrastructure.repository;

import com.yann.ordersservice.domain.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomOrderRepositoryImpl implements CustomOrderRepository {
    private final MongoTemplate mongoTemplate;

    public CustomOrderRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Order findOldestOrder() {
        Query query = new Query().limit(1);
        return mongoTemplate.findOne(query, Order.class);
    }

    @Override
    public Order findNewestOrder() {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "_id"))
                                 .limit(1);
        return mongoTemplate.findOne(query, Order.class);
    }
}
