package com.example.capitalgains.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TypeOperation {

    @JsonProperty("buy") BUY("BUY"),
    @JsonProperty("sell") SELL("SELL");

    private final String value;
}
