package com.example.capitalgains.unit.processor;

import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.processor.InputProcessor;
import com.example.capitalgains.processor.impl.InputStdinAssetOperations;
import com.example.capitalgains.utils.MapperUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static com.example.capitalgains.factory.InputFactory.ASSERT_OPERATION_1;
import static com.example.capitalgains.factory.InputFactory.ASSERT_OPERATION_2;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Timeout(2)
@Tag("unit")
@Tag("InputStdinAssetOperations")
class InputStdinAssetOperationsTest {

    private final MapperUtils mapperUtils = mock();
    private final InputProcessor<List<AssetOperation>, String> inputStdin = new InputStdinAssetOperations(mapperUtils);

    @Test
    @Tag("inputReader")
    @Tag("success")
    @DisplayName("Given the lineRead string, " +
            "When data is valid, " +
            "Should return a list of AssetOperation")
    void inputReaderTestSuccess1() {
        String lineRead = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]";
        List<AssetOperation> assetOperationList = List.of(ASSERT_OPERATION_1, ASSERT_OPERATION_2);

        when(mapperUtils.fromStringToListAssetOperation(lineRead)).thenReturn(assetOperationList);

        assertDoesNotThrow(() -> {
            List<AssetOperation> response = inputStdin.inputReader(lineRead);

            assertEquals(assetOperationList.size(), response.size());
            assertEquals(assetOperationList.getFirst(), response.getFirst());
            assertEquals(assetOperationList.getLast(), response.getLast());

            verify(mapperUtils, times(1)).fromStringToListAssetOperation(lineRead);
        });
    }

    @Test
    @Tag("inputReader")
    @Tag("fail")
    @DisplayName("Given the lineRead string, " +
            "When data is empty, " +
            "Should return a empty list of AssetOperation")
    void inputReaderTestFail1() {
        String lineRead = "";

        when(mapperUtils.fromStringToListAssetOperation(lineRead)).thenReturn(List.of());

        assertDoesNotThrow(() -> {
            List<AssetOperation> response = inputStdin.inputReader(lineRead);

            assertEquals(0, response.size());

            verify(mapperUtils, times(1)).fromStringToListAssetOperation(lineRead);
        });
    }
}
