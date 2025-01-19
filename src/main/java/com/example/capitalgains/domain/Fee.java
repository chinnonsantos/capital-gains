package com.example.capitalgains.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Fee {

    BigDecimal tax;
}
