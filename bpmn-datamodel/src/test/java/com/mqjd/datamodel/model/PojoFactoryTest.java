package com.mqjd.datamodel.model;

import com.mqjd.datamodel.field.IntegerField;
import com.mqjd.datamodel.field.StringField;
import com.mqjd.datamodel.field.array.ArrayField;
import org.junit.Test;

import java.util.Arrays;

public class PojoFactoryTest {

    @Test
    public void testPojoFactory() {
        PojoConfig pojoConfig = new PojoConfig();
        pojoConfig.setPackageName("org");
        pojoConfig.setClassName("Test");
        pojoConfig.setImports(Arrays.asList("java.util.Arrays", "java.util.Objects"));

        ArrayField integerField = new ArrayField();
        integerField.setTitle("test1");
        StringField stringField = new StringField();
        stringField.setTitle("test2");
        pojoConfig.setFields(Arrays.asList(integerField, stringField));
        PojoFactory.createPojo(pojoConfig, this.getClass().getClassLoader());
    }
}
