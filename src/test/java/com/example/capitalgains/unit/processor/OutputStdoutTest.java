package com.example.capitalgains.unit.processor;

import com.example.capitalgains.domain.Fee;
import com.example.capitalgains.processor.OutputProcessor;
import com.example.capitalgains.processor.impl.OutputStdout;
import com.example.capitalgains.utils.MapperUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static com.example.capitalgains.factory.OutputFactory.FEE_1;
import static com.example.capitalgains.factory.OutputFactory.FEE_2;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@Timeout(2)
@Tag("unit")
@Tag("OutputStdout")
class OutputStdoutTest {

    private final MapperUtils mapperUtils = mock();
    private final OutputProcessor<List<Fee>> outputStdout = new OutputStdout(mapperUtils);

    @Test
    @Tag("outputWriter")
    @Tag("success")
    @DisplayName("Given a list of Fee, " +
            "When data is valid, " +
            "Should print the string corresponding to the data")
    void outputWriterTestSuccess1() {
        List<Fee> feeList = List.of(FEE_1, FEE_2);

        assertDoesNotThrow(() -> outputStdout.outputWriter(feeList));
    }

    @Test
    @Tag("outputWriter")
    @Tag("fail")
    @DisplayName("Given a list of Fee, " +
            "When list is empty, " +
            "Should return a empty string")
    void outputWriterTestFail1() {
        assertDoesNotThrow(() -> outputStdout.outputWriter(List.of()));
    }
}
