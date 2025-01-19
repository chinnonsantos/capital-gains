package com.example.capitalgains.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetOperation {

    TypeOperation operation;

    @JsonProperty("unit-cost")
    BigDecimal unitCost;

    BigInteger quantity;
}
