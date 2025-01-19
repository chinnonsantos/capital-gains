package com.example.capitalgains.unit.utils;

import com.example.capitalgains.config.PropertiesConfig;
import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.domain.Fee;
import com.example.capitalgains.utils.MapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static com.example.capitalgains.factory.InputFactory.ASSERT_OPERATION_1;
import static com.example.capitalgains.factory.InputFactory.ASSERT_OPERATION_2;
import static com.example.capitalgains.factory.OutputFactory.FEE_1;
import static com.example.capitalgains.factory.OutputFactory.FEE_2;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@Timeout(2)
@Tag("unit")
@Tag("MapperUtils")
class MapperUtilsTest {

    private final PropertiesConfig propertiesConfig = mock();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MapperUtils mapperUtils = new MapperUtils(objectMapper, propertiesConfig);

    @Test
    @Tag("fromListFeeToString")
    @Tag("success")
    @DisplayName("Given a list of Fee, " +
            "When data is valid, " +
            "Should return the string corresponding to the data")
    void fromListFeeToStringTestSuccess1() {
        String feeStringify = "[{\"tax\":0.00},{\"tax\":10000.00}]";

        List<Fee> feeList = List.of(FEE_1, FEE_2);

        assertDoesNotThrow(() -> {
            String response = mapperUtils.fromListFeeToString(feeList);

            assertEquals(feeStringify, response);
        });
    }

    @Test
    @Tag("fromListFeeToString")
    @Tag("fail")
    @DisplayName("Given a list of Fee, " +
            "When list is empty, " +
            "Should return a string with empty list")
    void fromListFeeToStringTestFail1() {
        String feeStringify = List.of().toString();

        assertDoesNotThrow(() -> {
            String response = mapperUtils.fromListFeeToString(List.of());

            assertEquals(feeStringify, response);
        });
    }

    @Test
    @Tag("fromStringToListAssetOperation")
    @Tag("success")
    @DisplayName("Given a list of Fee, " +
            "When data is valid, " +
            "Should return the string corresponding to the data")
    void fromStringToListAssetOperationTestSuccess1() {
        String assetOperationStringify = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]";
        List<AssetOperation> assetOperationList = List.of(ASSERT_OPERATION_1, ASSERT_OPERATION_2);

        assertDoesNotThrow(() -> {
            List<AssetOperation> response = mapperUtils.fromStringToListAssetOperation(assetOperationStringify);

            assertEquals(assetOperationList, response);
        });
    }

    @Test
    @Tag("fromStringToListAssetOperation")
    @Tag("fail")
    @DisplayName("Given a list of Fee, " +
            "When list is empty, " +
            "Should return a string with empty list")
    void fromStringToListAssetOperationTestFail1() {
        String assetOperationStringify = "[]";

        assertDoesNotThrow(() -> {
            List<AssetOperation> response = mapperUtils.fromStringToListAssetOperation(assetOperationStringify);

            assertEquals(List.of(), response);
        });
    }
}
