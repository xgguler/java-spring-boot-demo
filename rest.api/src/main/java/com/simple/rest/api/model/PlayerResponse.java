package com.simple.rest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
@Getter
@Setter
public class PlayerResponse {

    @JsonProperty("win")
    private BigDecimal win;
}
