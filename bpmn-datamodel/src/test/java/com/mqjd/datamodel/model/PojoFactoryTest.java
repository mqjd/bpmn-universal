package com.mqjd.datamodel.model;

import com.mqjd.datamodel.field.BasicField;
import com.mqjd.datamodel.field.object.ObjectField;
import com.mqjd.datamodel.schema.Schema;
import com.mqjd.datamodel.schema.SchemaFactory;
import com.mqjd.datamodel.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Test;

public class PojoFactoryTest {

    @Test
    public void testPojoFactory() throws Exception {
        PojoConfig pojoConfig = new PojoConfig();
        pojoConfig.setPackageName("org");
        pojoConfig.setClassName("Test");
        BasicField basicField =
                SchemaFactory.createSchema(getClass().getResourceAsStream("/field/object1.json"));
        Assert.assertTrue(basicField instanceof ObjectField);
        Schema schema = JsonUtils.fromJson(JsonUtils.toJson(basicField), Schema.class);
        pojoConfig.setSchema(schema);
        Class<?> aClass = PojoFactory.createPojo(pojoConfig, this.getClass().getClassLoader());
        Object instance = aClass.newInstance();
        System.out.println(instance);
    }
}
