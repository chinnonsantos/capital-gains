package com.example.capitalgains.exception;

public class OverSellException extends RuntimeException {

    OverSellException(Throwable throwable) {
        super(throwable);
    }

    public OverSellException(String message) {
        super(message);
    }

    OverSellException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
