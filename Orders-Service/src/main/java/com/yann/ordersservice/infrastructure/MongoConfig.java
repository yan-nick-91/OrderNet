package com.yann.ordersservice.infrastructure;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.yann.ordersservice.infrastructure.converters.*;
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
    private String baseUri;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new CartIDReader(),
                new CartIDWriter(),
                new CustomerIDReader(),
                new CustomerIDWriter(),
                new EmailReader(),
                new EmailWriter(),
                new OrderIDReader(),
                new OrderIDWriter(),
                new SequenceReader(),
                new SequenceWriter(),
                new StreetNumberReader(),
                new StreetNumberWriter()
        ));
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(baseUri);
    }
}
