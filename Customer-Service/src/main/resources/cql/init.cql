-- create keyspace
CREATE KEYSPACE IF NOT EXISTS customer_keyspace
            WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE customer_keyspace;

-- UDT
CREATE TYPE IF NOT EXISTS address (
    zipcode text,
    street_name text,
    street_number text,
    city text,
    country text
);

-- Table
CREATE TABLE IF NOT EXISTS customers (
    id text PRIMARY KEY,
    firstname text,
    lastname text,
    email text,
    address frozen<address>
);