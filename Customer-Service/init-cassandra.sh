#!/bin/bash
set -e

echo "Cassandra is healthy, waiting 10 seconds..."
sleep 10

echo "Initializing Keyspace..."
cqlsh customer_cassandra -f /scripts/init.cql

echo "Keyspace successfully created!"
