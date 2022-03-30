package com.mqjd.datamodel.model;

import com.mqjd.datamodel.field.StringField;
import com.mqjd.datamodel.field.array.ArrayField;
import com.mqjd.datamodel.schema.Schema;
import org.junit.Test;

import java.util.Arrays;

public class PojoFactoryTest {

    @Test
    public void testPojoFactory() {
        PojoConfig pojoConfig = new PojoConfig();
        pojoConfig.setPackageName("org");
        pojoConfig.setClassName("Test");
        pojoConfig.setImports(Arrays.asList("java.util.Arrays", "java.util.Objects"));
        Schema schema = new Schema();
        ArrayField integerField = new ArrayField();
        integerField.setTitle("test1");
        schema.addField(integerField);
        StringField stringField = new StringField();
        stringField.setTitle("test2");
        schema.addField(stringField);
        pojoConfig.setSchema(schema);
        PojoFactory.createPojo(pojoConfig, this.getClass().getClassLoader());
    }
}
