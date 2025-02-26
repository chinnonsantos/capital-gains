package com.example.capitalgains.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OperationResponse {

    public OperationResponse(BigDecimal tax) {
        this.tax = tax;
        this.error = null;
    }

//    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    BigDecimal tax;

    String error;
}
