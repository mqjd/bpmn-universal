package com.mqjd.datamodel.model;

import com.mqjd.datamodel.field.BasicField;
import com.mqjd.datamodel.field.object.ObjectField;
import com.mqjd.datamodel.schema.Schema;
import com.mqjd.datamodel.utils.JsonUtils;
import com.mqjd.validator.SchemaValidator;
import org.junit.Assert;
import org.junit.Test;

public class PojoFactoryTest {

    @Test
    public void testPojoFactory() {
        PojoConfig pojoConfig = new PojoConfig();
        pojoConfig.setPackageName("org.mqjd");
        pojoConfig.setClassName("Test");
        BasicField basicField =
            JsonUtils.jsonToObject(getClass().getResourceAsStream("/model/object_schema.json"), BasicField.class);
        Assert.assertTrue(basicField instanceof ObjectField);
        Schema schema = JsonUtils.jsonToObject(JsonUtils.toJson(basicField), Schema.class);
        pojoConfig.setSchema(schema);
        Class<?> aClass = PojoFactory.createPojo(pojoConfig, this.getClass().getClassLoader());
        SchemaValidator jsonObject =
            (SchemaValidator)JsonUtils.jsonToObject(getClass().getResourceAsStream("/model/object_value.json"), aClass);
        System.out.println(JsonUtils.toJson(jsonObject));
        System.out.println(jsonObject.isValid());
    }
}
