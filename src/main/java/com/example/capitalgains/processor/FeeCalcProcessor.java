package com.example.capitalgains.processor;

public interface FeeCalcProcessor<T, U> {

    T weightedAveragePriceCalculator(U data);
}
