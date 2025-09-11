package com.hubertkarw.product.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ProductAppException {
    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
