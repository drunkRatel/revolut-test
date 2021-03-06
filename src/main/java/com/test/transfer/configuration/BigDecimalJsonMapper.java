package com.test.transfer.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalJsonMapper extends JsonSerializer<BigDecimal> {
        @Override
        public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(value.setScale(3, BigDecimal.ROUND_HALF_UP).toString());
        }
}
