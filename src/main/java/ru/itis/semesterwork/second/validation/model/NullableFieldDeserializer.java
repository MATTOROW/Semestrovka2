package ru.itis.semesterwork.second.validation.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class NullableFieldDeserializer extends StdDeserializer<NullableField<?>>
        implements ContextualDeserializer {

    private JavaType valueType;

    public NullableFieldDeserializer() {
        super(NullableField.class);
    }

    public NullableFieldDeserializer(JavaType valueType) {
        super(NullableField.class);
        this.valueType = valueType;
    }

    @Override
    public NullableField<?> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {

        if (p.getCurrentToken() == JsonToken.START_OBJECT) {
            JsonNode node = p.getCodec().readTree(p);
            if (node.has("value")) {
                JsonNode valueNode = node.get("value");
                if (valueNode.isNull()) {
                    return NullableField.nullValue();
                }
                Object value = ctxt.readTreeAsValue(valueNode, valueType);
                return NullableField.of(value);
            }
        }

        Object value = ctxt.readValue(p, valueType);
        return NullableField.of(value);
    }

    @Override
    public NullableField<?> getNullValue(DeserializationContext ctxt) {
        // Вызывается только когда поля вообще нет в JSON
        return NullableField.nullValue();
    }

    @Override
    public NullableField<?> getEmptyValue(DeserializationContext ctxt) {
        return NullableField.notProvided();
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt,
                                                BeanProperty property) throws JsonMappingException {

        if (property == null) {
            return this;
        }

        JavaType wrapperType = property.getType();
        JavaType containedType = wrapperType.containedType(0);
        return new NullableFieldDeserializer(containedType);
    }
}