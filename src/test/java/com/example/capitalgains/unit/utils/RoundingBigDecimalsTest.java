package com.example.capitalgains.unit.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Timeout(2)
@Tag("unit")
@Tag("RoundingBigDecimals")
class RoundingBigDecimalsTest {

    private static final int SCALE = 2;
    private static final int HIGH_SCALE = 15;
    private static final BigDecimal DIVIDEND = BigDecimal.valueOf(250);
    private static final BigDecimal DIVISOR = BigDecimal.valueOf(15);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Tag("noRoundingBigDecimals")
    @Tag("success")
    @DisplayName("Given a division of 250 (dividend) by 15 (divisor), " +
            "When mapping to JSON String (stringify) without handling, " +
            "Should return a value with repeating decimals (16.666666666666667)")
    void bigDecimalToJsonTestSuccess1() throws JsonProcessingException {
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        BigDecimal dividend = DIVIDEND.setScale(HIGH_SCALE, roundingMode);
        BigDecimal divisor = DIVISOR.setScale(HIGH_SCALE, roundingMode);
        String resultExpected = "16.666666666666667"; // 250 / 15 = 16.666666666666667

        BigDecimal result = dividend.divide(divisor, roundingMode)
                .setScale(HIGH_SCALE, roundingMode);
        String stringify = objectMapper.writeValueAsString(result);

        assertEquals(resultExpected, stringify);
    }

    @Test
    @Tag("noRoundingBigDecimals")
    @Tag("success")
    @DisplayName("Given a division of 250 (dividend) by 15 (divisor), " +
            "When mapping to JSON String (stringify) without handling, " +
            "Should return a value with repeating decimals (16.666666666666667)")
    void bigDecimalToJsonTestSuccess2() throws JsonProcessingException {
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        BigDecimal dividend = DIVIDEND.setScale(HIGH_SCALE, roundingMode);
        BigDecimal divisor = DIVISOR.setScale(HIGH_SCALE, roundingMode);
        String resultExpected = "16.666666666666667"; // 250 / 15 = 16.666666666666667

        BigDecimal result = dividend.divide(divisor, roundingMode)
                .setScale(HIGH_SCALE, roundingMode);
        String stringify = objectMapper.writeValueAsString(result);

        assertEquals(resultExpected, stringify);
    }

    @Test
    @Tag("noRoundingBigDecimals")
    @Tag("success")
    @DisplayName("Given a division of 250 (dividend) by 15 (divisor), " +
            "When there is HALF_UP rounding and scale by 15, " +
            "Should return a value with repeating decimals (16.666666666666667)")
    void roundingBigDecimalsTestSuccess1() {
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        BigDecimal dividend = DIVIDEND.setScale(HIGH_SCALE, roundingMode);
        BigDecimal divisor = DIVISOR.setScale(HIGH_SCALE, roundingMode);
        BigDecimal resultExpected = new BigDecimal("16.666666666666667"); // 250 / 15 = 16.666666666666667

        BigDecimal result = dividend.divide(divisor, roundingMode)
                .setScale(HIGH_SCALE, roundingMode);

        assertEquals(resultExpected, result);
    }

    @Test
    @Tag("noRoundingBigDecimals")
    @Tag("success")
    @DisplayName("Given a division of 250 (dividend) by 15 (divisor), " +
            "When there is UP rounding and scale by 2, " +
            "Should return a value without repeating decimals (16.67)")
    void roundingBigDecimalsTestSuccess2() {
        RoundingMode roundingMode = RoundingMode.UP;

        BigDecimal dividend = DIVIDEND.setScale(SCALE, roundingMode);
        BigDecimal divisor = DIVISOR.setScale(SCALE, roundingMode);
        BigDecimal resultExpected = new BigDecimal("16.67"); // 250 / 15 = 16.666666666666667

        BigDecimal result = dividend.divide(divisor, roundingMode)
                .setScale(SCALE, roundingMode);

        assertEquals(resultExpected, result);
    }

    @Test
    @Tag("noRoundingBigDecimals")
    @Tag("success")
    @DisplayName("Given a division of 250 (dividend) by 15 (divisor), " +
            "When there is DOWN rounding and scale by 2, " +
            "Should return a value without repeating decimals (16.66)")
    void roundingBigDecimalsTestSuccess3() {
        RoundingMode roundingMode = RoundingMode.DOWN;

        BigDecimal dividend = DIVIDEND.setScale(SCALE, roundingMode);
        BigDecimal divisor = DIVISOR.setScale(SCALE, roundingMode);
        BigDecimal resultExpected = new BigDecimal("16.66"); // 250 / 15 = 16.666666666666667

        BigDecimal result = dividend.divide(divisor, roundingMode)
                .setScale(SCALE, roundingMode);

        assertEquals(resultExpected, result);
    }

    @Test
    @Tag("noRoundingBigDecimals")
    @Tag("success")
    @DisplayName("Given a division of 250 (dividend) by 15 (divisor), " +
            "When there is CEILING rounding and scale by 2, " +
            "Should return a value without repeating decimals (16.67)")
    void roundingBigDecimalsTestSuccess4() {
        RoundingMode roundingMode = RoundingMode.CEILING;

        BigDecimal dividend = DIVIDEND.setScale(SCALE, roundingMode);
        BigDecimal divisor = DIVISOR.setScale(SCALE, roundingMode);
        BigDecimal resultExpected = new BigDecimal("16.67"); // 250 / 15 = 16.666666666666667

        BigDecimal result = dividend.divide(divisor, roundingMode)
                .setScale(SCALE, roundingMode);

        assertEquals(resultExpected, result);
    }

    @Test
    @Tag("noRoundingBigDecimals")
    @Tag("success")
    @DisplayName("Given a division of 250 (dividend) by 15 (divisor), " +
            "When there is FLOOR rounding and scale by 2, " +
            "Should return a value without repeating decimals (16.66)")
    void roundingBigDecimalsTestSuccess5() {
        RoundingMode roundingMode = RoundingMode.FLOOR;

        BigDecimal dividend = DIVIDEND.setScale(SCALE, roundingMode);
        BigDecimal divisor = DIVISOR.setScale(SCALE, roundingMode);
        BigDecimal resultExpected = new BigDecimal("16.66"); // 250 / 15 = 16.666666666666667

        BigDecimal result = dividend.divide(divisor, roundingMode)
                .setScale(SCALE, roundingMode);

        assertEquals(resultExpected, result);
    }

    @Test
    @Tag("noRoundingBigDecimals")
    @Tag("success")
    @DisplayName("Given a division of 250 (dividend) by 15 (divisor), " +
            "When there is HALF_UP rounding and scale by 2, " +
            "Should return a value without repeating decimals (16.67)")
    void roundingBigDecimalsTestSuccess6() {
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        BigDecimal dividend = DIVIDEND.setScale(SCALE, roundingMode);
        BigDecimal divisor = DIVISOR.setScale(SCALE, roundingMode);
        BigDecimal resultExpected = new BigDecimal("16.67"); // 250 / 15 = 16.666666666666667

        BigDecimal result = dividend.divide(divisor, roundingMode)
                .setScale(SCALE, roundingMode);

        assertEquals(resultExpected, result);
    }

    @Test
    @Tag("noRoundingBigDecimals")
    @Tag("success")
    @DisplayName("Given a division of 250 (dividend) by 15 (divisor), " +
            "When there is HALF_DOWN rounding and scale by 2, " +
            "Should return a value without repeating decimals (16.67)")
    void roundingBigDecimalsTestSuccess7() {
        RoundingMode roundingMode = RoundingMode.HALF_DOWN;

        BigDecimal dividend = DIVIDEND.setScale(SCALE, roundingMode);
        BigDecimal divisor = DIVISOR.setScale(SCALE, roundingMode);
        BigDecimal resultExpected = new BigDecimal("16.67"); // 250 / 15 = 16.666666666666667

        BigDecimal result = dividend.divide(divisor, roundingMode)
                .setScale(SCALE, roundingMode);

        assertEquals(resultExpected, result);
    }

    @Test
    @Tag("noRoundingBigDecimals")
    @Tag("success")
    @DisplayName("Given a division of 250 (dividend) by 15 (divisor), " +
            "When there is HALF_EVEN rounding and scale by 2, " +
            "Should return a value without repeating decimals (16.67)")
    void roundingBigDecimalsTestSuccess8() {
        RoundingMode roundingMode = RoundingMode.HALF_EVEN;

        BigDecimal dividend = DIVIDEND.setScale(SCALE, roundingMode);
        BigDecimal divisor = DIVISOR.setScale(SCALE, roundingMode);
        BigDecimal resultExpected = new BigDecimal("16.67"); // 250 / 15 = 16.666666666666667

        BigDecimal result = dividend.divide(divisor, roundingMode)
                .setScale(SCALE, roundingMode);

        assertEquals(resultExpected, result);
    }

    @Test
    @Tag("noRoundingBigDecimals")
    @Tag("fail")
    @DisplayName("Given a division of 250 (dividend) by 15 (divisor), " +
            "When there is UNNECESSARY rounding and scale by 2, " +
            "Should throw an ArithmeticException")
    void roundingBigDecimalsTestFail1() {
        RoundingMode roundingMode = RoundingMode.UNNECESSARY;

        BigDecimal dividend = DIVIDEND.setScale(SCALE, roundingMode);
        BigDecimal divisor = DIVISOR.setScale(SCALE, roundingMode);

        String errorExpected = "Rounding necessary";

        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> dividend.divide(divisor, roundingMode)
        );

        assertEquals(errorExpected, exception.getMessage());
    }
}
