package com.mqjd.datamodel.field;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class StringFieldTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void fromJson() throws IOException {
        StringField objectField =
                OBJECT_MAPPER.readValue(
                        getClass().getResourceAsStream("/field/string1.json"), StringField.class);
        assertEquals("test_string", objectField.getTitle());
    }
}
