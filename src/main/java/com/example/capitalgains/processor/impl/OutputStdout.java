package com.example.capitalgains.processor.impl;

import com.example.capitalgains.domain.OperationResponse;
import com.example.capitalgains.processor.OutputProcessor;
import com.example.capitalgains.utils.MapperUtils;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OutputStdout implements OutputProcessor<List<OperationResponse>> {

    private final MapperUtils mapperUtils;

    @Override
    @SuppressWarnings("java:S106")
    public void outputWriter(List<OperationResponse> operationResponseList) {
        System.out.println(
                mapperUtils.fromListFeeToString(operationResponseList)
        );
    }
}
