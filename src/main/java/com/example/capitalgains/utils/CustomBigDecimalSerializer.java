package com.example.capitalgains.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CustomBigDecimalSerializer extends StdSerializer<BigDecimal> {

    protected CustomBigDecimalSerializer(Class<BigDecimal> bigDecimalClass) {
        super(bigDecimalClass);
    }

    @Override
    public void serialize(
            BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeString(
                bigDecimal.setScale(2, RoundingMode.HALF_UP).toString()
        );
    }
}
