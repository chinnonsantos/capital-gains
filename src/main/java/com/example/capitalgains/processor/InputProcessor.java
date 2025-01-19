package com.example.capitalgains.processor;

public interface InputProcessor<T, U> {

    T inputReader(U data);
}
