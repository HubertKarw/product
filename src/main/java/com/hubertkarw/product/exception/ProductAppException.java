package com.hubertkarw.product.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductAppException extends RuntimeException {

    private LocalDateTime timestamp;
    private HttpStatus status;

    public ProductAppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
