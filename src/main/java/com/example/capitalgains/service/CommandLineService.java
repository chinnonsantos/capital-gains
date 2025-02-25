package com.example.capitalgains.service;

import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.domain.OperationResponse;
import com.example.capitalgains.processor.FeeCalcProcessor;
import com.example.capitalgains.processor.InputProcessor;
import com.example.capitalgains.processor.OutputProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class CommandLineService {

    private final BufferedReader bufferedReader;
    private final InputProcessor<List<AssetOperation>, String> inputStdin;
    private final FeeCalcProcessor<List<OperationResponse>, List<AssetOperation>> feeCalc;
    private final OutputProcessor<List<OperationResponse>> outputStdout;

    public void runner() {
        log.debug("start command line runner");

        String lineRead;

        try {
            while ((lineRead = bufferedReader.readLine()) != null) {
                if (StringUtils.isEmpty(lineRead)) break;

                List<AssetOperation> assetOperationList = inputStdin.inputReader(lineRead);

                List<OperationResponse> operationResponseList = feeCalc.weightedAveragePriceCalculator(assetOperationList);

                outputStdout.outputWriter(operationResponseList);
            }
        } catch (IOException e) {
            log.error("read line error | {}", e.getMessage(), e.getCause());
        }

        log.debug("end command line runner");
    }
}
