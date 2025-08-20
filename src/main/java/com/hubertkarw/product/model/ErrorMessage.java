package com.hubertkarw.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timeStamp;
}
