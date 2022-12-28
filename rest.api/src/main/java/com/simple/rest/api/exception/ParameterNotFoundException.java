package com.simple.rest.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ParameterNotFoundException extends RuntimeException {
    private final String errorCode;
    private final int statusCode;

    public ParameterNotFoundException(String errorCode, String message, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = httpStatus.value();
    }
}
