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
    public void testGenClassPojoFactory() {
        GenClassLoader classLoader = new GenClassLoader(this.getClass().getClassLoader());
        PojoConfig pojoConfig = new PojoConfig();
        pojoConfig.setPackageName("org.mqjd");
        pojoConfig.setClassName("Test");
        BasicField basicField =
                SchemaFactory.createSchema(getClass().getResourceAsStream("/model/object1.json"));
        Assert.assertTrue(basicField instanceof ObjectField);
        Schema schema = JsonUtils.fromJson(JsonUtils.toJson(basicField), Schema.class);
        pojoConfig.setSchema(schema);
        Class<?> aClass = classLoader.genPojo(pojoConfig);
        Object jsonObject =
                JsonUtils.fromJson(getClass().getResourceAsStream("/model/object1.json"), aClass);
        System.out.println(JsonUtils.toJson(jsonObject));
    }

    @Test
    public void testPojoFactory() {
        PojoConfig pojoConfig = new PojoConfig();
        pojoConfig.setPackageName("org.mqjd");
        pojoConfig.setClassName("Test");
        BasicField basicField =
                SchemaFactory.createSchema(getClass().getResourceAsStream("/model/object1.json"));
        Assert.assertTrue(basicField instanceof ObjectField);
        ((ObjectField) basicField).setRequired(new String[] {"properties", "required"});
        Schema schema = JsonUtils.fromJson(JsonUtils.toJson(basicField), Schema.class);
        pojoConfig.setSchema(schema);
        Class<?> aClass = PojoFactory.createPojo(pojoConfig, this.getClass().getClassLoader());
        Object jsonObject =
                JsonUtils.fromJson(getClass().getResourceAsStream("/model/object1.json"), aClass);
        System.out.println(JsonUtils.toJson(jsonObject));
    }
}
