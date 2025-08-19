package com.yann.inventoryservice.infrastructure;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.yann.inventoryservice.infrastructure.converters.ProductIDReader;
import com.yann.inventoryservice.infrastructure.converters.ProductIDWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${base.uri}")
    private String uri;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new ProductIDReader(),
                new ProductIDWriter()
        ));
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(uri);
    }
}
