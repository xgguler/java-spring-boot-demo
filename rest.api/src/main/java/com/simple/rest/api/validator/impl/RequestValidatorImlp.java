package com.simple.rest.api.validator.impl;

import com.simple.rest.api.constants.ErrorMessage;
import com.simple.rest.api.exception.ParameterNotFoundException;
import com.simple.rest.api.model.PlayerRequest;
import com.simple.rest.api.validator.RequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RequestValidatorImlp implements RequestValidator {
    @Override
    public void validate(PlayerRequest request) {
        if( request.getBet() == null || request.getNumber() < 1 || request.getNumber() > 100 ) {
            throw new ParameterNotFoundException(ErrorMessage.INVALID_PARAMETER_ERROR_CODE,
                    ErrorMessage.INVALID_PARAMETER_ERROR_MESSAGE,
                    HttpStatus.BAD_REQUEST);
        }
    }
}
