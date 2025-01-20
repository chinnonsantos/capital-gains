package com.example.capitalgains.processor.impl;

import com.example.capitalgains.domain.Accumulator;
import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.domain.Fee;
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
public class FeeCalcImpl implements FeeCalcProcessor<List<Fee>, List<AssetOperation>> {

    private final MapperUtils mapperUtils;

    //  operationCeiling = 20_000  (sellQty * sellUnitCost)
    //  taxPercentage = 0.20
    //
    //  accumulateLoss = 0
    //  currentGain = 0
    //  balance = 0  (currentGain - accumulateLoss)
    //  currentQty = 0
    //  currentWeightedAverage = 0
    //
    //  newWeightedAverage = {5}( {3}( {1}(currentQty * currentWeightedAverage) + {2}(buyQty * buyUnitCost) )
    //          / {4}(currentQty + buyQty) )
    //
    //  currentWeightedAverage = newWeightedAverage

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
    public List<Fee> weightedAveragePriceCalculator(List<AssetOperation> assetOperationList) {
        // TODO: set from ENVs!!!
        BigDecimal operationCeiling = mapperUtils.bigDecimalScaled(20_000.00);
        BigDecimal taxPercentage = mapperUtils.bigDecimalScaled(0.20);
        BigDecimal biggedDecimalZero = mapperUtils.bigDecimalZero();

        ArrayList<Fee> feeArrayList = new ArrayList<>();
        Accumulator accumulator = new Accumulator(biggedDecimalZero, biggedDecimalZero, biggedDecimalZero);
        log.info("===> assetOperationList: {}", assetOperationList);

        for (AssetOperation assetOperation : assetOperationList) {
            log.info("===> start loop -> accumulator: {}", accumulator);
            log.info("===> start loop -> assetOperation: {}", assetOperation);
            BigDecimal totalTaxes = biggedDecimalZero;

            // Nenhum imposto é pago em operações de compra.
            if (assetOperation.getOperation() == TypeOperation.BUY) {
                log.info("===> BUY: Operação de compra");

                BigDecimal newCurrentQty = mapperUtils
                        .bigDecimalAdd(accumulator.getCurrentQty(), assetOperation.getQuantity());
                BigDecimal newCurrentWeightedAverage = calcNewWeightedAveragePrice(accumulator, assetOperation);
                accumulator = new Accumulator(
                        accumulator.getAccumulateLoss(), newCurrentQty, newCurrentWeightedAverage
                );
                feeArrayList.add(new Fee(totalTaxes));
                log.info("===> continue feeArrayList: {} accumulator: {} %n Nenhum imposto é pago em operações de compra.",
                        feeArrayList, accumulator);
                continue;
            }

            BigDecimal newCurrentQty = mapperUtils
                    .bigDecimalSubtract(accumulator.getCurrentQty(), assetOperation.getQuantity());

            // O percentual de imposto pago é de 20% sobre o lucro obtido na operação. Ou seja, o imposto vai ser
            // pago quando há uma operação de venda cujo preço é superior ao preço médio ponderado de compra.
            log.info("===> sellUnitCost {} > CurrentWeightedAverage {}: compareTo {}",
                    assetOperation.getUnitCost(), accumulator.getCurrentWeightedAverage(),
                    assetOperation.getUnitCost().compareTo(accumulator.getCurrentWeightedAverage()));

            if (assetOperation.getUnitCost().compareTo(accumulator.getCurrentWeightedAverage()) > 0) {
                log.info("===> calcula lucro ou perda para pagar impostos");

                // gain - calculate balance and taxes
                BigDecimal totalGainUnit = mapperUtils
                        .bigDecimalSubtract(assetOperation.getUnitCost(), accumulator.getCurrentWeightedAverage());
                BigDecimal totalGainOperation = mapperUtils
                        .bigDecimalMultiply(totalGainUnit, assetOperation.getQuantity());
                BigDecimal newAccumulateLoss;

                // (totalGainOperation > accumulateLoss) -> pay fee, accumulateLoss = 0
                log.info("===> totalGainOperation {} > accumulateLoss {}: compareTo {}",
                        totalGainOperation, accumulator.getAccumulateLoss(), totalGainOperation.compareTo(accumulator.getAccumulateLoss()));

                if (totalGainOperation.compareTo(accumulator.getAccumulateLoss()) > 0) {
                    log.info("===> Operação com lucro superior as perdas, zera perdas, paga impostos da diferença.");
                    totalGainOperation = mapperUtils.bigDecimalSubtract(totalGainOperation, accumulator.getAccumulateLoss());
                    newAccumulateLoss = biggedDecimalZero;

                    // Você não paga nenhum imposto se o valor total da operação (custo unitário da ação x quantidade)
                    // for menor ou igual a R$ 20000,00.
                    BigDecimal totalOperation = mapperUtils
                            .bigDecimalMultiply(assetOperation.getUnitCost(), assetOperation.getQuantity());
                    log.info("===> SELL: Operação de venda - totalOperation {} > {} compareTo {}",
                            totalOperation, operationCeiling, totalOperation.compareTo(operationCeiling));

                    if (totalOperation.compareTo(operationCeiling) > 0) {
                        totalTaxes = mapperUtils.bigDecimalMultiply(totalGainOperation, taxPercentage);

                        log.info("===> Operação superior a {} com lucro, calcula {}% de imposto devido, resultado {}",
                                operationCeiling, taxPercentage, totalTaxes);
                    }

                } else {
                    log.info("===> Operação com lucro inferior as perdas, deduz perdas, não paga impostos.");

                    newAccumulateLoss = mapperUtils.bigDecimalSubtract(accumulator.getAccumulateLoss(), totalGainOperation);
                }

                accumulator = new Accumulator(
                        newAccumulateLoss, newCurrentQty, accumulator.getCurrentWeightedAverage()
                );
                feeArrayList.add(new Fee(totalTaxes));
                log.info("===> continue feeArrayList: {} accumulator: {}", feeArrayList, accumulator);
                continue;
            }

            // loss - calculate balance
            log.info("===> Operação com perda, acumula perdas, não paga impostos.");
            BigDecimal totalLossUnit = mapperUtils
                    .bigDecimalSubtract(accumulator.getCurrentWeightedAverage(), assetOperation.getUnitCost());
            BigDecimal totalLossOperation = mapperUtils
                    .bigDecimalMultiply(totalLossUnit, assetOperation.getQuantity());
            BigDecimal newAccumulateLoss = mapperUtils.bigDecimalAdd(accumulator.getAccumulateLoss(), totalLossOperation);

            accumulator = new Accumulator(
                    newAccumulateLoss, newCurrentQty, accumulator.getCurrentWeightedAverage()
            );
            feeArrayList.add(new Fee(totalTaxes));
            log.info("===> endLoop feeArrayList: {} accumulator: {}", feeArrayList, accumulator);
        }

        log.info("===> return feeArrayList: {}", feeArrayList);
        return feeArrayList.stream().toList();
    }
}
