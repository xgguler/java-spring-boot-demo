package com.simple.rest.api.validator;

import com.simple.rest.api.model.PlayerRequest;
import org.springframework.stereotype.Service;

@Service
public interface RequestValidator {

    void validate(PlayerRequest request);
}
