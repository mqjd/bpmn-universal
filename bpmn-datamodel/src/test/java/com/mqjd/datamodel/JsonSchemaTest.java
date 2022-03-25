package com.mqjd.datamodel;

import com.fasterxml.jackson.databind.JsonNode;
import com.mqjd.datamodel.utils.JsonUtils;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class JsonSchemaTest {
    private static final JsonSchemaFactory JSON_SCHEMA_FACTORY =
            JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

    @Test
    public void jsonSchemaTest() {
        JsonSchema schema =
                JSON_SCHEMA_FACTORY.getSchema(getClass().getResourceAsStream("/testSchema.json"));
        JsonNode dataJsonNode = JsonUtils.read(getClass().getResourceAsStream("/testData.json"));
        Set<ValidationMessage> validationMessages = schema.validate(dataJsonNode);
        assertTrue(validationMessages.isEmpty());
    }
}
