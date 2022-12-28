package com.simple.rest.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
@Getter
@Setter
public class PlayerRequest {

    private BigDecimal bet;

    private int number;
}
