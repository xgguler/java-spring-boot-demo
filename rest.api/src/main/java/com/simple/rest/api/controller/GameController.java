package com.simple.rest.api.controller;

import com.simple.rest.api.exception.ParameterNotFoundException;
import com.simple.rest.api.model.PlayerRequest;
import com.simple.rest.api.model.PlayerResponse;
import com.simple.rest.api.service.GameService;
import com.simple.rest.api.validator.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
public class GameController {

    private final GameService service;

    private final RequestValidator validator;

    @PostMapping(value = "/play", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerResponse> play(@RequestBody PlayerRequest request) {
        try {
            validator.validate(request);
            PlayerResponse playerResponse = service.calculateWin(request);
            return new ResponseEntity<>(playerResponse, HttpStatus.OK);
        } catch (ParameterNotFoundException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getErrorCode(), e);
        }
    }
}
