package com.example.capitalgains.processor.impl;

import com.example.capitalgains.config.PropertiesConfig;
import com.example.capitalgains.domain.Accumulator;
import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.domain.OperationResponse;
import com.example.capitalgains.domain.TypeOperation;
import com.example.capitalgains.processor.FeeCalcProcessor;
import com.example.capitalgains.utils.MapperUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class FeeCalcImpl implements FeeCalcProcessor<List<OperationResponse>, List<AssetOperation>> {

    private final MapperUtils mapperUtils;
    private final PropertiesConfig propertiesConfig;

    //  newWeightedAverage = {5}( {3}( {1}(currentQty * currentWeightedAverage) + {2}(buyQty * buyUnitCost) )
    //          / {4}(currentQty + buyQty) )
    private BigDecimal calcNewWeightedAveragePrice(Accumulator accumulator, AssetOperation buyAssetOperation) {
        BigDecimal currentQty = accumulator.getCurrentQty();
        BigDecimal currentWeightedAverage = accumulator.getCurrentWeightedAverage();

        BigDecimal buyQty = mapperUtils.bigDecimalScaled(buyAssetOperation.getQuantity());
        BigDecimal buyUnitCost = mapperUtils.bigDecimalScaled(buyAssetOperation.getUnitCost());

        BigDecimal calc1 = mapperUtils.bigDecimalMultiply(currentQty, currentWeightedAverage);
        BigDecimal calc2 = mapperUtils.bigDecimalMultiply(buyQty, buyUnitCost);
        BigDecimal calc3 = mapperUtils.bigDecimalAdd(calc1, calc2);
        BigDecimal calc4 = mapperUtils.bigDecimalAdd(currentQty, buyQty);

        return mapperUtils.bigDecimalDivide(calc3, calc4);
    }

    @Override
    public List<OperationResponse> weightedAveragePriceCalculator(List<AssetOperation> assetOperationList) {
        log.info("line handles {} operations", assetOperationList.size());

        BigDecimal operationCeiling = propertiesConfig.getApp().getOperationCeiling();
        BigDecimal taxPercentage = propertiesConfig.getApp().getTaxPercentage();
        BigDecimal bigDecimalZero = mapperUtils.bigDecimalZero();

        ArrayList<OperationResponse> operationResponseArrayList = new ArrayList<>();
        Accumulator accumulator = new Accumulator(bigDecimalZero, bigDecimalZero, bigDecimalZero);

        for (AssetOperation assetOperation : assetOperationList) {
            BigDecimal totalTaxes = bigDecimalZero;

            // BUY - No tax is paid on purchase transactions.
            if (assetOperation.getOperation() == TypeOperation.BUY) {
                BigDecimal newCurrentQty = mapperUtils
                        .bigDecimalAdd(accumulator.getCurrentQty(), assetOperation.getQuantity());
                BigDecimal newCurrentWeightedAverage = calcNewWeightedAveragePrice(accumulator, assetOperation);
                accumulator = new Accumulator(
                        accumulator.getAccumulateLoss(), newCurrentQty, newCurrentWeightedAverage
                );
                operationResponseArrayList.add(new OperationResponse(totalTaxes, null));
                continue;
            }

            if (assetOperation.getQuantity().compareTo(accumulator.getCurrentQty().toBigInteger()) > 0) {
                operationResponseArrayList
                        .add(new OperationResponse(null, "Can't sell more stocks than you have"));
                continue;
            }

            BigDecimal newCurrentQty = mapperUtils
                    .bigDecimalSubtract(accumulator.getCurrentQty(), assetOperation.getQuantity());

            // SELL - The tax percentage paid is 20% on the profit obtained from the sales transaction.
            if (assetOperation.getUnitCost().compareTo(accumulator.getCurrentWeightedAverage()) > 0) {

                // gain - calculate balance and taxes
                BigDecimal totalGainUnit = mapperUtils
                        .bigDecimalSubtract(assetOperation.getUnitCost(), accumulator.getCurrentWeightedAverage());
                BigDecimal totalGainOperation = mapperUtils
                        .bigDecimalMultiply(totalGainUnit, assetOperation.getQuantity());
                BigDecimal newAccumulateLoss;

                // (totalGainOperation > accumulateLoss) -> pay fee, accumulateLoss = 0
                if (totalGainOperation.compareTo(accumulator.getAccumulateLoss()) > 0) {

                    totalGainOperation = mapperUtils
                            .bigDecimalSubtract(totalGainOperation, accumulator.getAccumulateLoss());
                    newAccumulateLoss = bigDecimalZero;

                    // You do not pay any tax if the total value of the transaction (unit cost of the share x quantity)
                    // is less than or equal to R$ 20.000,00.
                    BigDecimal totalOperation = mapperUtils
                            .bigDecimalMultiply(assetOperation.getUnitCost(), assetOperation.getQuantity());
                    if (totalOperation.compareTo(operationCeiling) > 0) {
                        totalTaxes = mapperUtils.bigDecimalMultiply(totalGainOperation, taxPercentage);
                    }

                } else {
                    newAccumulateLoss = mapperUtils
                            .bigDecimalSubtract(accumulator.getAccumulateLoss(), totalGainOperation);
                }

                accumulator = new Accumulator(
                        newAccumulateLoss, newCurrentQty, accumulator.getCurrentWeightedAverage()
                );
                operationResponseArrayList.add(new OperationResponse(totalTaxes, null));
                continue;
            }

            // loss - calculate balance
            BigDecimal totalLossUnit = mapperUtils
                    .bigDecimalSubtract(accumulator.getCurrentWeightedAverage(), assetOperation.getUnitCost());
            BigDecimal totalLossOperation = mapperUtils
                    .bigDecimalMultiply(totalLossUnit, assetOperation.getQuantity());
            BigDecimal newAccumulateLoss = mapperUtils
                    .bigDecimalAdd(accumulator.getAccumulateLoss(), totalLossOperation);

            accumulator = new Accumulator(
                    newAccumulateLoss, newCurrentQty, accumulator.getCurrentWeightedAverage()
            );
            operationResponseArrayList.add(new OperationResponse(totalTaxes, null));
        }

        return operationResponseArrayList.stream().toList();
    }
}
