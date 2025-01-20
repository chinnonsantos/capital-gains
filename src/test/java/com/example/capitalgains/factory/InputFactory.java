package com.example.capitalgains.factory;

import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.domain.TypeOperation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class InputFactory {

    public static final RoundingMode ROUNDING_MODE_HALF_UP = RoundingMode.HALF_UP;
    public static final Integer SCALE = 2;
    public static final AssetOperation ASSERT_OPERATION_1 = new AssetOperation(
            TypeOperation.BUY,
            BigDecimal.valueOf(10.00).setScale(SCALE, ROUNDING_MODE_HALF_UP),
            BigInteger.valueOf(10_000)
    );
    public static final AssetOperation ASSERT_OPERATION_2 = new AssetOperation(
            TypeOperation.SELL,
            BigDecimal.valueOf(20.00).setScale(SCALE, ROUNDING_MODE_HALF_UP),
            BigInteger.valueOf(5_000)
    );

    public static AssetOperation createBuyOperation(double unitCost, long quantity) {
        return new AssetOperation(
                TypeOperation.BUY,
                BigDecimal.valueOf(unitCost).setScale(SCALE, ROUNDING_MODE_HALF_UP),
                BigInteger.valueOf(quantity)
        );
    }

    public static AssetOperation createSellOperation(double unitCost, long quantity) {
        return new AssetOperation(
                TypeOperation.SELL,
                BigDecimal.valueOf(unitCost).setScale(SCALE, ROUNDING_MODE_HALF_UP),
                BigInteger.valueOf(quantity)
        );
    }
}
