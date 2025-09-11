package com.hubertkarw.product.exception;

import com.hubertkarw.product.model.ErrorMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ProductAppExceptionHandler {


    @ExceptionHandler(ProductAppException.class)
    ResponseEntity<ErrorMessage> handleProductAppException(ProductAppException exception) {
        log.warn("ProductAppException: status={}, message={}", exception.getStatus(), exception.getMessage());
        return ResponseEntity.status(exception.getStatus())
                .body(new ErrorMessage(exception.getStatus().value(), exception.getStatus().getReasonPhrase(), exception.getMessage(), exception.getTimestamp()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorMessage> handleExceptions(Exception exception) {
        log.error("Unexpected Exception occurred with message:{}",exception.getMessage() , exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unknown Error", LocalDateTime.now()));
    }
}
