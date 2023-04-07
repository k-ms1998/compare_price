package com.example.comparePrice.exception;

import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceCompareException.class)
    public ResponseEntity<?> applicationHandler(PriceCompareException e) {
        log.error("Error Occurred: {}, {}", e.getStatus().toString(), e.getMessage());

        return ResponseEntity.status(e.getStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(ConnectTimeoutException.class)
    public ResponseEntity<?> applicationHandler(ConnectTimeoutException e) {
        log.error("Error Occurred: {}, {}", HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
