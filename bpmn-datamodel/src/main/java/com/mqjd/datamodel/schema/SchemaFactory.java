package com.mqjd.datamodel.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.mqjd.datamodel.field.*;
import com.mqjd.datamodel.field.array.ArrayField;
import com.mqjd.datamodel.field.object.ObjectField;
import com.mqjd.datamodel.utils.JsonUtils;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.google.common.base.Preconditions.checkNotNull;

public class SchemaFactory {

    public static BasicField createSchema(String json) {
        return createField(JsonUtils.read(json));
    }

    public static BasicField createSchema(InputStream inputStream) {
        return createField(JsonUtils.read(inputStream));
    }

    private static BasicField createField(JsonNode node) {
        BasicType basicType = getBasicType(node);
        switch (basicType) {
            case STRING:
                return createStringField(node);
            case INTEGER:
                return createIntegerField(node);
            case NUMBER:
                return createNumberField(node);
            case OBJECT:
                return createObjectField(node);
            case BOOLEAN:
                return createBooleanField(node);
            case ARRAY:
                return createArrayField(node);
            case NULL:
                return createNullField(node);
            default:
                throw new IllegalArgumentException("node type is not valid");
        }
    }

    private static StringField createStringField(JsonNode node) {
        return new StringField();
    }

    private static IntegerField createIntegerField(JsonNode node) {
        return new IntegerField();
    }

    private static NumberField createNumberField(JsonNode node) {
        return new NumberField();
    }

    private static ObjectField createObjectField(JsonNode node) {
        ObjectField objectField = new ObjectField();
        Map<String, BasicField> properties = new HashMap<>();
        objectField.setProperties(properties);
        Iterator<Map.Entry<String, JsonNode>> fieldIterator = node.fields();
        while (fieldIterator.hasNext()) {
            Map.Entry<String, JsonNode> nodeEntry = fieldIterator.next();
            properties.put(nodeEntry.getKey(), createField(nodeEntry.getValue()));
        }
        return objectField;
    }

    private static ArrayField createArrayField(JsonNode node) {
        ArrayField arrayField = new ArrayField();
        Set<BasicField> basicFields =
                StreamSupport.stream(
                                Spliterators.spliteratorUnknownSize(
                                        node.elements(), Spliterator.ORDERED),
                                false)
                        .map(SchemaFactory::createField)
                        .collect(Collectors.toSet());
        if (basicFields.size() == 1) {
            arrayField.setItems(basicFields.stream().findFirst().get());
        } else {
            BasicField basicField = new BasicField();
            basicField.setAnyOf(basicFields.toArray(new BasicField[0]));
            arrayField.setItems(basicField);
        }
        return arrayField;
    }

    private static BooleanField createBooleanField(JsonNode node) {
        return new BooleanField();
    }

    private static NullField createNullField(JsonNode node) {
        return new NullField();
    }

    private static BasicType getBasicType(JsonNode node) {
        checkNotNull(node, "node can not be null");
        checkNotNull(node.getNodeType(), "node type can not be null");
        return BasicType.fromString(node.getNodeType().name());
    }
}
