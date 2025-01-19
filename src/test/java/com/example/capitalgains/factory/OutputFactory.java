package com.example.capitalgains.factory;

import com.example.capitalgains.domain.Fee;

import java.math.BigDecimal;

import static com.example.capitalgains.factory.InputFactory.ROUNDING_MODE_HALF_UP;
import static com.example.capitalgains.factory.InputFactory.SCALE;

public class OutputFactory {

    public static final Fee FEE_1 = new Fee(BigDecimal.valueOf(0).setScale(SCALE, ROUNDING_MODE_HALF_UP));
    public static final Fee FEE_2 = new Fee(BigDecimal.valueOf(10000.00).setScale(SCALE, ROUNDING_MODE_HALF_UP));
    public static final Fee FEE_ZERO = FEE_1;
}
