package com.yann.inventoryservice.domain.utils;

public interface CreateIDFactory<ID> extends IDFactory<ID> {
    ID create();
}
