package com.example.capitalgains.unit.processor;

import com.example.capitalgains.config.PropertiesConfig;
import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.domain.Fee;
import com.example.capitalgains.processor.FeeCalcProcessor;
import com.example.capitalgains.processor.impl.FeeCalcImpl;
import com.example.capitalgains.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static com.example.capitalgains.factory.InputFactory.ASSERT_OPERATION_1;
import static com.example.capitalgains.factory.InputFactory.ASSERT_OPERATION_2;
import static com.example.capitalgains.factory.InputFactory.ROUNDING_MODE_HALF_UP;
import static com.example.capitalgains.factory.InputFactory.SCALE;
import static com.example.capitalgains.factory.InputFactory.createBuyOperation;
import static com.example.capitalgains.factory.InputFactory.createSellOperation;
import static com.example.capitalgains.factory.OutputFactory.FEE_1;
import static com.example.capitalgains.factory.OutputFactory.FEE_2;
import static com.example.capitalgains.factory.OutputFactory.FEE_TEN_THOUSAND;
import static com.example.capitalgains.factory.OutputFactory.FEE_ZERO;
import static com.example.capitalgains.factory.OutputFactory.createFee;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

@Timeout(15)
@Tag("unit")
@Tag("FeeCalcImpl")
class FeeCalcImplTest {

    private FeeCalcProcessor<List<Fee>, List<AssetOperation>> feeCalc;

    @BeforeEach
    void setUp() {
        PropertiesConfig.App appProps = new PropertiesConfig.App();
        appProps.setRoundingMode(ROUNDING_MODE_HALF_UP);
        appProps.setScale(SCALE);

        PropertiesConfig propertiesConfig = new PropertiesConfig();
        propertiesConfig.setApp(appProps);

        MapperUtils mapperUtils = new MapperUtils(mock(), propertiesConfig);
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

        List<Fee> feeList = List.of(FEE_1, FEE_2);

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
        AssetOperation operation1 = createBuyOperation(10, 100);
        AssetOperation operation2 = createSellOperation(15, 50);
        List<AssetOperation> assetOperationList = List.of(operation1, operation2, operation2);

        List<Fee> feeList = List.of(FEE_ZERO, FEE_ZERO, FEE_ZERO);

        List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

        assertEquals(feeList, response);
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #2] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess2() {
        AssetOperation operation1 = createBuyOperation(10, 10_000);
        AssetOperation operation2 = createSellOperation(20, 5_000);
        AssetOperation operation3 = createSellOperation(5, 5_000);
        List<AssetOperation> assetOperationList = List.of(operation1, operation2, operation3);

        List<Fee> feeList = List.of(FEE_ZERO, FEE_TEN_THOUSAND, FEE_ZERO);

        List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

        assertEquals(feeList, response);
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #1 + Case #2] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess1and2() {
        AssetOperation operation1a = createBuyOperation(10, 100);
        AssetOperation operation2a = createSellOperation(15, 50);
        List<AssetOperation> assetOperationListA = List.of(operation1a, operation2a, operation2a);

        List<Fee> feeListA = List.of(FEE_ZERO, FEE_ZERO, FEE_ZERO);

        List<Fee> responseA = feeCalc.weightedAveragePriceCalculator(assetOperationListA);

        assertEquals(feeListA, responseA);

        AssetOperation operation1b = createBuyOperation(10, 10_000);
        AssetOperation operation2b = createSellOperation(20, 5_000);
        AssetOperation operation3b = createSellOperation(5, 5_000);
        List<AssetOperation> assetOperationListB = List.of(operation1b, operation2b, operation3b);

        List<Fee> feeListB = List.of(FEE_ZERO, FEE_TEN_THOUSAND, FEE_ZERO);

        List<Fee> responseB = feeCalc.weightedAveragePriceCalculator(assetOperationListB);

        assertEquals(feeListB, responseB);

        assertNotEquals(assetOperationListA, assetOperationListB);
        assertNotEquals(responseA, responseB);
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #3] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess3() {
        AssetOperation operation1 = createBuyOperation(10, 10_000);
        AssetOperation operation2 = createSellOperation(5, 5_000);
        AssetOperation operation3 = createSellOperation(20, 3_000);
        List<AssetOperation> assetOperationList = List.of(operation1, operation2, operation3);

        List<Fee> feeList = List.of(FEE_ZERO, FEE_ZERO, createFee(1_000));

        List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

        assertEquals(feeList, response);
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #4] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess4() {
        AssetOperation operation1 = createBuyOperation(10, 10_000);
        AssetOperation operation2 = createBuyOperation(25, 5_000);
        AssetOperation operation3 = createSellOperation(15, 10_000);
        List<AssetOperation> assetOperationList = List.of(operation1, operation2, operation3);

        List<Fee> feeList = List.of(FEE_ZERO, FEE_ZERO, FEE_ZERO);

        List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

        assertEquals(feeList, response);
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #5] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess5() {
        AssetOperation operation1 = createBuyOperation(10, 10_000);
        AssetOperation operation2 = createBuyOperation(25, 5_000);
        AssetOperation operation3 = createSellOperation(15, 10_000);
        AssetOperation operation4 = createSellOperation(25, 5_000);
        List<AssetOperation> assetOperationList = List.of(operation1, operation2, operation3, operation4);

        List<Fee> feeList = List.of(FEE_ZERO, FEE_ZERO, FEE_ZERO, FEE_TEN_THOUSAND);

        List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

        assertEquals(feeList, response);
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #6] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess6() {
        AssetOperation operation1 = createBuyOperation(10, 10_000);
        AssetOperation operation2 = createSellOperation(2, 5_000);
        AssetOperation operation3 = createSellOperation(20, 2_000);
        AssetOperation operation5 = createSellOperation(25, 1_000);
        List<AssetOperation> assetOperationList = List.of(operation1, operation2, operation3, operation3, operation5);

        List<Fee> feeList = List.of(FEE_ZERO, FEE_ZERO, FEE_ZERO, FEE_ZERO, createFee(3_000));

        List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

        assertEquals(feeList, response);
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #7] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess7() {
        AssetOperation operation1 = createBuyOperation(10, 10_000);
        AssetOperation operation2 = createSellOperation(2, 5_000);
        AssetOperation operation3 = createSellOperation(20, 2_000);
        AssetOperation operation5 = createSellOperation(25, 1_000);
        AssetOperation operation6 = createBuyOperation(20, 10_000);
        AssetOperation operation7 = createSellOperation(15, 5_000);
        AssetOperation operation8 = createSellOperation(30, 4_350);
        AssetOperation operation9 = createSellOperation(30, 650);
        List<AssetOperation> assetOperationList = List.of(
                operation1, operation2, operation3, operation3, operation5,
                operation6, operation7, operation8, operation9
        );

        List<Fee> feeList = List.of(
                FEE_ZERO, FEE_ZERO, FEE_ZERO, FEE_ZERO, createFee(3_000),
                FEE_ZERO, FEE_ZERO, createFee(3_700), FEE_ZERO
        );

        List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

        assertEquals(feeList, response);
    }

    @Test
    @Tag("weightedAveragePriceCalculator")
    @Tag("success")
    @DisplayName("[Case #8] Given a list of AssetOperation, " +
            "When data is valid, " +
            "Should return the corresponding list of Fee")
    void weightedAveragePriceCalculatorTestSuccess8() {
        AssetOperation operation1 = createBuyOperation(10, 10_000);
        AssetOperation operation2 = createSellOperation(50, 10_000);
        AssetOperation operation3 = createBuyOperation(20, 10_000);
        AssetOperation operation4 = createSellOperation(50, 10_000);
        List<AssetOperation> assetOperationList = List.of(operation1, operation2, operation3, operation4);

        List<Fee> feeList = List.of(FEE_ZERO, createFee(80_000), FEE_ZERO, createFee(60_000));

        List<Fee> response = feeCalc.weightedAveragePriceCalculator(assetOperationList);

        assertEquals(feeList, response);
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
