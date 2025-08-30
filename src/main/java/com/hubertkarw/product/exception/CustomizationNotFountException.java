package com.hubertkarw.product.exception;

import org.springframework.http.HttpStatus;

public class CustomizationNotFountException extends ProductAppException{
    public CustomizationNotFountException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
