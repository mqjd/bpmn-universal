package com.mqjd.datamodel.field;

import com.mqjd.datamodel.utils.JsonUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringFieldTest {

    @Test
    public void fromJson() {
        StringField stringField =
                JsonUtils.jsonToObject(
                        getClass().getResourceAsStream("/field/string1.json"), StringField.class);
        assertEquals("test_string", stringField.getTitle());
        System.out.println(JsonUtils.toJson(stringField));
    }
}
