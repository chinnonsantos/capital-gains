package com.example.capitalgains.processor.impl;

import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.domain.Fee;
import com.example.capitalgains.processor.FeeCalcProcessor;
import com.example.capitalgains.utils.MapperUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class FeeCalcImpl implements FeeCalcProcessor<List<Fee>, List<AssetOperation>> {

    private final MapperUtils mapperUtils;

    @Override
    public List<Fee> weightedAveragePriceCalculator(List<AssetOperation> assetOperationList) {

//        BigDecimal bd1 = mapperUtils.bigDecimalScaled("250");
//        BigDecimal bd2 = mapperUtils.bigDecimalScaled(15);
//
//        BigDecimal sum = mapperUtils.bigDecimalAdd(bd1, bd2);
//        BigDecimal difference = mapperUtils.bigDecimalSubtract(bd1, bd2);
//        BigDecimal quotient = mapperUtils.bigDecimalDivide(bd1, bd2);
//        BigDecimal product = mapperUtils.bigDecimalMultiply(bd1, bd2);
//
//        log.info("====> sum {}, difference {}, quotient {}, product {}", sum, difference, quotient, product);

        return assetOperationList.stream()
                .map(assetOperation -> new Fee(assetOperation.getUnitCost())).toList();
    }
}
