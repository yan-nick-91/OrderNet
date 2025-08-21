package com.yann.customerservices.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.convert.Neo4jConversions;

import java.util.List;

@Configuration
public class Neo4JConfig {

    @Bean
    public Neo4jConversions neo4jConversions() {
        return new Neo4jConversions(List.of(
                new CustomerIDReader(),
                new CustomerIDWriter(),
                new EmailReader(),
                new EmailWriter(),
                new ProductIDReader(),
                new ProductIDWriter(),
                new StreetNumberReader(),
                new StreetNumberWriter()
        ));
    }
}
