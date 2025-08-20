package com.hubertkarw.product.exception;

import com.hubertkarw.product.model.ErrorMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice

public class ProductAppExceptionHandler {
    @ExceptionHandler(ProductAppException.class)
    ResponseEntity<ErrorMessage> handleProductAppException(ProductAppException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(new ErrorMessage(exception.getStatus().value(), exception.getStatus().getReasonPhrase(), exception.getMessage(), exception.getTimestamp()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorMessage> handleExceptions(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unknown Error", LocalDateTime.now()));
    }
}
