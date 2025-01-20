package com.example.capitalgains.factory;

import com.example.capitalgains.domain.Fee;

import java.math.BigDecimal;

import static com.example.capitalgains.factory.InputFactory.ROUNDING_MODE_HALF_UP;
import static com.example.capitalgains.factory.InputFactory.SCALE_2;

public class OutputFactory {

    public static final Fee FEE_1 = new Fee(BigDecimal.valueOf(0.00).setScale(SCALE_2, ROUNDING_MODE_HALF_UP));
    public static final Fee FEE_2 = new Fee(BigDecimal.valueOf(10_000.00).setScale(SCALE_2, ROUNDING_MODE_HALF_UP));
    public static final Fee FEE_ZERO = FEE_1;
    public static final Fee FEE_TEN_THOUSAND = FEE_2;

    public static Fee createFee(double tax) {
        return new Fee(BigDecimal.valueOf(tax).setScale(SCALE_2, ROUNDING_MODE_HALF_UP));
    }
}
