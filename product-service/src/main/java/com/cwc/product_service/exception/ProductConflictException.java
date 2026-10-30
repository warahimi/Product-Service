package com.cwc.product_service.exception;

public class ProductConflictException
        extends RuntimeException {

    public ProductConflictException(String message) {
        super(message);
    }
}
