package com.example.capitalgains.unit.service;

import com.example.capitalgains.config.PropertiesConfig;
import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.domain.OperationResponse;
import com.example.capitalgains.processor.FeeCalcProcessor;
import com.example.capitalgains.processor.InputProcessor;
import com.example.capitalgains.processor.OutputProcessor;
import com.example.capitalgains.service.CommandLineService;
import com.example.capitalgains.utils.MapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.InOrder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static com.example.capitalgains.commons.AbstractReader.bufferedReadExamplesTxt;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Timeout(2)
@Tag("unit")
@Tag("CommandLineService")
class CommandLineServiceTest {

    private final PropertiesConfig propertiesConfig = mock();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MapperUtils mapperUtils = new MapperUtils(objectMapper, propertiesConfig);
    private final InputProcessor<List<AssetOperation>, String> inputStdin = mock();
    private final FeeCalcProcessor<List<OperationResponse>, List<AssetOperation>> feeCalc = mock();
    private final OutputProcessor<List<OperationResponse>> outputStdout = mock();

    @Test
    @Tag("runner")
    @Tag("success")
    @DisplayName("Given the input of two lines, " +
            "When data is valid Asset Operations, " +
            "Should print the respective Fees")
    void runnerTestSuccess1() throws IOException {
        BufferedReader bufferedReaderInput1 = bufferedReadExamplesTxt("input-1-success");

        String inputLine1 = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]";
        String inputLine2 = "[{\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\": 10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":10.00, \"quantity\": 5000}]";

        List<AssetOperation> assetOperationList1 = mapperUtils.fromStringToListAssetOperation(inputLine1);
        List<AssetOperation> assetOperationList2 = mapperUtils.fromStringToListAssetOperation(inputLine2);
        List<OperationResponse> operationResponseList1 = assetOperationList1.stream()
                .map(assetOperation -> new OperationResponse(assetOperation.getUnitCost())).toList();
        List<OperationResponse> operationResponseList2 = assetOperationList2.stream()
                .map(assetOperation -> new OperationResponse(assetOperation.getUnitCost())).toList();

        when(inputStdin.inputReader(inputLine1)).thenReturn(assetOperationList1);
        when(inputStdin.inputReader(inputLine2)).thenReturn(assetOperationList2);
        when(feeCalc.weightedAveragePriceCalculator(assetOperationList1)).thenReturn(operationResponseList1);
        when(feeCalc.weightedAveragePriceCalculator(assetOperationList2)).thenReturn(operationResponseList2);

        CommandLineService commandLineService =
                new CommandLineService(bufferedReaderInput1, inputStdin, feeCalc, outputStdout);

        commandLineService.runner();

        InOrder inOrder = inOrder(inputStdin, feeCalc, outputStdout);

        inOrder.verify(inputStdin, times(1)).inputReader(inputLine1);
        inOrder.verify(feeCalc, times(1)).weightedAveragePriceCalculator(assetOperationList1);
        inOrder.verify(outputStdout, times(1)).outputWriter(operationResponseList1);
        inOrder.verify(inputStdin, times(1)).inputReader(inputLine2);
        inOrder.verify(feeCalc, times(1)).weightedAveragePriceCalculator(assetOperationList2);
        inOrder.verify(outputStdout, times(1)).outputWriter(operationResponseList2);

        verify(inputStdin, times(2)).inputReader(any());
        verify(feeCalc, times(2)).weightedAveragePriceCalculator(anyList());
        verify(outputStdout, times(2)).outputWriter(anyList());
    }

    @Test
    @Tag("runner")
    @Tag("fail")
    @DisplayName("Given the empty input, " +
            "Should not process anything")
    void runnerTestFail1() throws IOException {
        BufferedReader bufferedReaderInput1 = bufferedReadExamplesTxt("input-1-fail");

        CommandLineService commandLineService =
                new CommandLineService(bufferedReaderInput1, inputStdin, feeCalc, outputStdout);

        assertDoesNotThrow(commandLineService::runner);

        verify(inputStdin, never()).inputReader(any());
        verify(feeCalc, never()).weightedAveragePriceCalculator(anyList());
        verify(outputStdout, never()).outputWriter(anyList());
    }
}
