package com.example.capitalgains.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accumulator {

    BigDecimal accumulateLoss;

    BigDecimal currentQty;

    BigDecimal currentWeightedAverage;
}
