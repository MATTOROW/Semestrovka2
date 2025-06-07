package ru.itis.semesterwork.second.validation.model;

import com.fasterxml.jackson.annotation.JsonValue;

public record NullableField<T>(boolean isPresent, T value) {

    public static <T> NullableField<T> notProvided() {
        return new NullableField<>(false, null);
    }

    public static <T> NullableField<T> of(T value) {
        return new NullableField<>(true, value);
    }
    public static <T> NullableField<T> nullValue() {
        return new NullableField<>(true, null);
    }

    @JsonValue
    public T toJson() {
        return value;
    }
}
