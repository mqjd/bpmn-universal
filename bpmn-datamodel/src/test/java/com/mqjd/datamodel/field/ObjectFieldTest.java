package com.mqjd.datamodel.field;

import com.mqjd.datamodel.field.object.ObjectField;
import com.mqjd.datamodel.utils.JsonUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObjectFieldTest {

    @Test
    public void fromJson() {
        ObjectField objectField =
                JsonUtils.jsonToObject(
                        getClass().getResourceAsStream("/field/object1.json"), ObjectField.class);
        assertEquals("test_object", objectField.getTitle());
        System.out.println(JsonUtils.toJson(objectField));
    }
}
