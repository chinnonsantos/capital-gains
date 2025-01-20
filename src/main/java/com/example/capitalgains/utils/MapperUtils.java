package com.example.capitalgains.utils;

import com.example.capitalgains.config.PropertiesConfig;
import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.domain.Fee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class MapperUtils {

    private final ObjectMapper objectMapper;
    private final PropertiesConfig propertiesConfig;

    public BigDecimal bigDecimalAdd(BigDecimal addend, BigInteger augend) {
        return addend
                .add(new BigDecimal(augend))
                .setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public BigDecimal bigDecimalAdd(BigDecimal addend, BigDecimal augend) {
        return addend
                .add(augend)
                .setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public BigDecimal bigDecimalSubtract(BigDecimal minuend, BigInteger subtrahend) {
        return minuend
                .subtract(new BigDecimal(subtrahend))
                .setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public BigDecimal bigDecimalSubtract(BigDecimal minuend, BigDecimal subtrahend) {
        return minuend
                .subtract(subtrahend)
                .setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public BigDecimal bigDecimalDivide(BigDecimal dividend, BigDecimal divisor) {
        return dividend
                .divide(divisor, propertiesConfig.getApp().getRoundingMode())
                .setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public BigDecimal bigDecimalMultiply(BigDecimal multiplicand, BigInteger multiplier) {
        return multiplicand
                .multiply(new BigDecimal(multiplier))
                .setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public BigDecimal bigDecimalMultiply(BigDecimal multiplicand, BigDecimal multiplier) {
        return multiplicand
                .multiply(multiplier)
                .setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public BigDecimal bigDecimalScaled(BigDecimal value) {
        return value.setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public BigDecimal bigDecimalScaled(BigInteger value) {
        return new BigDecimal(value.toString())
                .setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public BigDecimal bigDecimalScaled(double value) {
        return BigDecimal
                .valueOf(value)
                .setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public BigDecimal bigDecimalScaled(String value) {
        return new BigDecimal(value)
                .setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public BigDecimal bigDecimalZero() {
        return BigDecimal.ZERO
                .setScale(propertiesConfig.getApp().getScale(), propertiesConfig.getApp().getRoundingMode());
    }

    public String fromListFeeToString(List<Fee> feeList) {
        try {
            return objectMapper.writeValueAsString(feeList);
        } catch (JsonProcessingException e) {
            log.error("failed to serialize List<Fee> to JSON String (Stringify) | {}",
                    e.getMessage(), e.getCause());
        }
        return List.of().toString();
    }

    public List<AssetOperation> fromStringToListAssetOperation(String jsonStringify) {
        try {
            return objectMapper.readValue(jsonStringify, new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            log.error("failed to deserialize JSON String (Stringify) to List<AssetOperation> | {}",
                    e.getMessage(), e.getCause());
        }
        return List.of();
    }
}
