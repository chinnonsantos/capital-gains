package com.example.capitalgains.factory;

import com.example.capitalgains.domain.OperationResponse;

import java.math.BigDecimal;

import static com.example.capitalgains.factory.InputFactory.ROUNDING_MODE_HALF_UP;
import static com.example.capitalgains.factory.InputFactory.SCALE_2;

public class OutputFactory {

    public static final OperationResponse OPERATION_RESPONSE_1 = new OperationResponse(BigDecimal.valueOf(0.00).setScale(SCALE_2, ROUNDING_MODE_HALF_UP), null);
    public static final OperationResponse OPERATION_RESPONSE_2 = new OperationResponse(BigDecimal.valueOf(10_000.00).setScale(SCALE_2, ROUNDING_MODE_HALF_UP), null);
    public static final OperationResponse OPERATION_RESPONSE_ZERO = OPERATION_RESPONSE_1;
    public static final OperationResponse OPERATION_RESPONSE_TEN_THOUSAND = OPERATION_RESPONSE_2;

    public static OperationResponse createFee(double tax) {
        return new OperationResponse(BigDecimal.valueOf(tax).setScale(SCALE_2, ROUNDING_MODE_HALF_UP), null);
    }
}
