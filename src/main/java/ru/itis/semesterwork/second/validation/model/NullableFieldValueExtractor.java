package ru.itis.semesterwork.second.validation.model;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;

public class NullableFieldValueExtractor implements ValueExtractor<NullableField<@ExtractedValue ?>> {
    @Override
    public void extractValues(NullableField<?> originalValue, ValueReceiver receiver) {
        if (originalValue != null && originalValue.isPresent()) {
            receiver.value(null, originalValue.value());
        }
    }
}

