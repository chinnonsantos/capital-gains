package com.example.capitalgains.unit.processor;

import com.example.capitalgains.config.PropertiesConfig;
import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.domain.Fee;
import com.example.capitalgains.domain.TypeOperation;
import com.example.capitalgains.processor.FeeCalcProcessor;
import com.example.capitalgains.processor.impl.FeeCalcImpl;
import com.example.capitalgains.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

import static com.example.capitalgains.factory.InputFactory.ASSERT_OPERATION_1;
import static com.example.capitalgains.factory.InputFactory.ASSERT_OPERATION_2;
import static com.example.capitalgains.factory.InputFactory.ROUNDING_MODE_HALF_UP;
import static com.example.capitalgains.factory.InputFactory.SCALE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@Timeout(15)
@Tag("unit")
@Tag("FeeCalcImpl")
class FeeCalcImplTest {

    private MapperUtils mapperUtils;
    private FeeCalcProcessor<List<Fee>, List<AssetOperation>> feeCalc;

    @BeforeEach
    void setUp() {
        PropertiesConfig.App appProps = new PropertiesConfig.App();
        appProps.setRoundingMode(ROUNDING_MODE_HALF_UP);
        appProps.setScale(SCALE);

        PropertiesConfig propertiesConfig = new PropertiesConfig();
        propertiesConfig.setApp(appProps);

        mapperUtils = new MapperUtils(mock(), propertiesConfig);
        feeCalc = new FeeCalcImpl(mapperUtils);
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess0() {
        List<AssetOperation> assetOperationList = List.of(ASSERT_OPERATION_1, ASSERT_OPERATION_2);

        List<Fee> feeList = assetOperationList.stream()
                .map(assetOperation -> new Fee(assetOperation.getUnitCost())).toList();

        assertDoesNotThrow(() -> {
            List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

            assertEquals(feeList, response);
        });
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #1] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess1() {
        AssetOperation operation1 = new AssetOperation(
                TypeOperation.BUY,
                mapperUtils.bigDecimalScaled(10.00),
                BigInteger.valueOf(100)
        );
        AssetOperation operation2 = new AssetOperation(
                TypeOperation.SELL,
                BigDecimal.valueOf(15.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(50)
        );
        List<AssetOperation> assetOperationList = List.of(operation1, operation2, operation2);

        Fee fee1 = new Fee(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP));
        Fee fee2 = new Fee(BigDecimal.valueOf(10000.00).setScale(2, RoundingMode.HALF_UP));

        List<Fee> feeList = assetOperationList.stream()
                .map(assetOperation -> new Fee(assetOperation.getUnitCost())).toList();

        assertDoesNotThrow(() -> {
            List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

            assertEquals(feeList, response);
        });
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #2] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess2() {
        AssetOperation operation1 = new AssetOperation(
                TypeOperation.BUY,
                BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(10000)
        );
        AssetOperation operation2 = new AssetOperation(
                TypeOperation.SELL,
                BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(5000)
        );
        List<AssetOperation> assetOperationList = List.of(operation1, operation2);

        Fee fee1 = new Fee(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP));
        Fee fee2 = new Fee(BigDecimal.valueOf(10000.00).setScale(2, RoundingMode.HALF_UP));

        List<Fee> feeList = assetOperationList.stream()
                .map(assetOperation -> new Fee(assetOperation.getUnitCost())).toList();

        assertDoesNotThrow(() -> {
            List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

            assertEquals(feeList, response);
        });
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #3] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess3() {
        AssetOperation operation1 = new AssetOperation(
                TypeOperation.BUY,
                BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(10000)
        );
        AssetOperation operation2 = new AssetOperation(
                TypeOperation.SELL,
                BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(5000)
        );
        List<AssetOperation> assetOperationList = List.of(operation1, operation2);

        Fee fee1 = new Fee(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP));
        Fee fee2 = new Fee(BigDecimal.valueOf(10000.00).setScale(2, RoundingMode.HALF_UP));

        List<Fee> feeList = assetOperationList.stream()
                .map(assetOperation -> new Fee(assetOperation.getUnitCost())).toList();

        assertDoesNotThrow(() -> {
            List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

            assertEquals(feeList, response);
        });
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #4] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess4() {
        AssetOperation operation1 = new AssetOperation(
                TypeOperation.BUY,
                BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(10000)
        );
        AssetOperation operation2 = new AssetOperation(
                TypeOperation.SELL,
                BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(5000)
        );
        List<AssetOperation> assetOperationList = List.of(operation1, operation2);

        Fee fee1 = new Fee(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP));
        Fee fee2 = new Fee(BigDecimal.valueOf(10000.00).setScale(2, RoundingMode.HALF_UP));

        List<Fee> feeList = assetOperationList.stream()
                .map(assetOperation -> new Fee(assetOperation.getUnitCost())).toList();

        assertDoesNotThrow(() -> {
            List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

            assertEquals(feeList, response);
        });
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #5] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess5() {
        AssetOperation operation1 = new AssetOperation(
                TypeOperation.BUY,
                BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(10000)
        );
        AssetOperation operation2 = new AssetOperation(
                TypeOperation.SELL,
                BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(5000)
        );
        List<AssetOperation> assetOperationList = List.of(operation1, operation2);

        Fee fee1 = new Fee(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP));
        Fee fee2 = new Fee(BigDecimal.valueOf(10000.00).setScale(2, RoundingMode.HALF_UP));

        List<Fee> feeList = assetOperationList.stream()
                .map(assetOperation -> new Fee(assetOperation.getUnitCost())).toList();

        assertDoesNotThrow(() -> {
            List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

            assertEquals(feeList, response);
        });
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #6] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess6() {
        AssetOperation operation1 = new AssetOperation(
                TypeOperation.BUY,
                BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(10000)
        );
        AssetOperation operation2 = new AssetOperation(
                TypeOperation.SELL,
                BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(5000)
        );
        List<AssetOperation> assetOperationList = List.of(operation1, operation2);

        Fee fee1 = new Fee(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP));
        Fee fee2 = new Fee(BigDecimal.valueOf(10000.00).setScale(2, RoundingMode.HALF_UP));

        List<Fee> feeList = assetOperationList.stream()
                .map(assetOperation -> new Fee(assetOperation.getUnitCost())).toList();

        assertDoesNotThrow(() -> {
            List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

            assertEquals(feeList, response);
        });
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #7] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess7() {
        AssetOperation operation1 = new AssetOperation(
                TypeOperation.BUY,
                BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(10000)
        );
        AssetOperation operation2 = new AssetOperation(
                TypeOperation.SELL,
                BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(5000)
        );
        List<AssetOperation> assetOperationList = List.of(operation1, operation2);

        Fee fee1 = new Fee(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP));
        Fee fee2 = new Fee(BigDecimal.valueOf(10000.00).setScale(2, RoundingMode.HALF_UP));

        List<Fee> feeList = assetOperationList.stream()
                .map(assetOperation -> new Fee(assetOperation.getUnitCost())).toList();

        assertDoesNotThrow(() -> {
            List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

            assertEquals(feeList, response);
        });
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #8] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess8() {
        AssetOperation operation1 = new AssetOperation(
                TypeOperation.BUY,
                BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(10000)
        );
        AssetOperation operation2 = new AssetOperation(
                TypeOperation.SELL,
                BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP),
                BigInteger.valueOf(5000)
        );
        List<AssetOperation> assetOperationList = List.of(operation1, operation2);

        Fee fee1 = new Fee(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP));
        Fee fee2 = new Fee(BigDecimal.valueOf(10000.00).setScale(2, RoundingMode.HALF_UP));

        List<Fee> feeList = assetOperationList.stream()
                .map(assetOperation -> new Fee(assetOperation.getUnitCost())).toList();

        assertDoesNotThrow(() -> {
            List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

            assertEquals(feeList, response);
        });
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("fail")
    @DisplayName("Given a list of AssetOperation, " +
            "When list is empty, " +
            "Should return a empty list of Fee")
    void weightedAveragePriceCalculatorTestFail1() {
        assertDoesNotThrow(() -> {
            List<Fee> response = feeCalc.weightedAveragePriceCalculator(List.of());

            assertEquals(List.of(), response);
        });
    }
}
