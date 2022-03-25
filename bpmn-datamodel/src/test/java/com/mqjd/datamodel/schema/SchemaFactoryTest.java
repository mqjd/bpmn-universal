package com.mqjd.datamodel.schema;

import com.mqjd.datamodel.field.BasicField;
import com.mqjd.datamodel.field.array.ArrayField;
import com.mqjd.datamodel.field.object.ObjectField;
import com.mqjd.datamodel.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Test;

public class SchemaFactoryTest {

    @Test
    public void testCreateObjectField() {
        BasicField basicField = SchemaFactory.createSchema("{}");
        Assert.assertTrue(basicField instanceof ObjectField);
        System.out.println(JsonUtils.toJson(basicField));
    }

    @Test
    public void testCreateArrayField() {
        BasicField basicField = SchemaFactory.createSchema("[1,\"2\",3]");
        Assert.assertTrue(basicField instanceof ArrayField);
        System.out.println(JsonUtils.toJson(basicField));
    }

    @Test
    public void testCreateMixedArrayField() {
        BasicField basicField =
                SchemaFactory.createSchema(getClass().getResourceAsStream("/schema/array1.json"));
        Assert.assertTrue(basicField instanceof ArrayField);
        System.out.println(JsonUtils.toJson(basicField));
    }
}
