package com.example.capitalgains.processor.impl;

import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.processor.InputProcessor;
import com.example.capitalgains.utils.MapperUtils;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class InputStdinAssetOperations implements InputProcessor<List<AssetOperation>, String> {

    private final MapperUtils mapperUtils;

    @Override
    public List<AssetOperation> inputReader(String lineRead) {
        return mapperUtils.fromStringToListAssetOperation(lineRead);
    }
}
