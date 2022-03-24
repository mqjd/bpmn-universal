package com.mqjd.datamodel.field;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BasicTypeTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void fromJson() throws IOException {
    BasicType basicType = OBJECT_MAPPER.readValue("\"string\"", BasicType.class);
    assertEquals(BasicType.STRING, basicType);
  }
}
