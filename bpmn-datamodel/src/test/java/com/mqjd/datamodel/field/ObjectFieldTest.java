package com.mqjd.datamodel.field;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ObjectFieldTest {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void fromJson() throws IOException {
    ObjectField objectField =
        OBJECT_MAPPER.readValue(
            getClass().getResourceAsStream("/field/object1.json"), ObjectField.class);
    assertEquals("test_object", objectField.getTitle());
    System.out.println(OBJECT_MAPPER.writeValueAsString(objectField));
  }
}
