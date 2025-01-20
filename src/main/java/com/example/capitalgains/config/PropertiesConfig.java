package com.example.capitalgains.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Component
@ConfigurationProperties()
@EnableConfigurationProperties
public class PropertiesConfig {

    @Data
    public static class App {
        private RoundingMode roundingMode;
        private Integer scale;
        private BigDecimal operationCeiling;
        private BigDecimal taxPercentage;
    }

    private App app;
}
