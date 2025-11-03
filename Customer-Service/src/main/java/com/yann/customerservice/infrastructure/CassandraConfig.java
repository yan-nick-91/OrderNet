package com.yann.customerservice.infrastructure;

import com.yann.customerservice.infrastructure.converters.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;

import java.util.List;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
    @Value("${spring.cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${spring.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.cassandra.port}")
    private int port;

    @Value("${spring.cassandra.local-datacenter}")
    private String localDatacenter;

    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected String getLocalDataCenter() {
        return localDatacenter;
    }

    @Override
    public CassandraCustomConversions customConversions() {
        return new CassandraCustomConversions(List.of(
                new CustomerIDReader(),
                new CustomerIDWriter(),
                new EmailReader(),
                new EmailWriter(),
                new StreetNumberReader(),
                new StreetNumberWriter()
        ));
    }
}
