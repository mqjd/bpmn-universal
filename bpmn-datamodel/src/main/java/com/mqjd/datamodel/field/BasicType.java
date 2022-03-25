package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mqjd.datamodel.field.array.ArrayField;
import com.mqjd.datamodel.field.object.ObjectField;

import java.util.Locale;

public enum BasicType {
    STRING(StringField.class),
    INTEGER(IntegerField.class),
    NUMBER(NumberField.class),
    OBJECT(ObjectField.class),
    ARRAY(ArrayField.class),
    ANY(BasicField.class),
    BOOLEAN(BooleanField.class),
    NULL(NullField.class);

    private final Class<? extends BasicField> clz;

    BasicType(Class<? extends BasicField> clz) {
        this.clz = clz;
    }

    public Class<? extends BasicField> getClz() {
        return clz;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static BasicType fromString(String key) {
        BasicType[] values = values();
        for (BasicType value : values) {
            if (value.name().equalsIgnoreCase(key)) {
                return value;
            }
        }
        return null;
    }

    @JsonValue
    public String getString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
