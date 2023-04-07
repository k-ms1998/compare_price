package com.example.comparePrice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class PriceCompareException extends RuntimeException {

    private  HttpStatus status;
    private  String message;

    public PriceCompareException(HttpStatus status) {
        this.status = status;
        this.message = "";
    }

    @Override
    public String getMessage() {
        return String.format("%s. %s", status.toString(), this.message);
    }
}
