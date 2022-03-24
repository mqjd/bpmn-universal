package com.mqjd;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class JsonSchemaTest {
  private static final JsonSchemaFactory JSON_SCHEMA_FACTORY =
      JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void jsonSchemaTest() throws IOException {
    JsonSchema schema =
        JSON_SCHEMA_FACTORY.getSchema(getClass().getResourceAsStream("/testSchema.json"));
    JsonNode dataJsonNode =
        OBJECT_MAPPER.readTree(getClass().getResourceAsStream("/testData.json"));
    Set<ValidationMessage> validationMessages = schema.validate(dataJsonNode);
    assertTrue(validationMessages.isEmpty());
  }
}
