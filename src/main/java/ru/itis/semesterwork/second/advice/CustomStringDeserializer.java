package ru.itis.semesterwork.second.advice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class CustomStringDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String value = p.getValueAsString();
        if (value == null) {
            return null;
        }
        String trimmed = StringUtils.normalizeSpace(value);
        return trimmed.isEmpty() ? null : trimmed;
    }
}
