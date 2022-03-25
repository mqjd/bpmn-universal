package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class NullField extends BasicField {
    @JsonProperty("default")
    private Object defaultValue;

    @Override
    public BasicType getType() {
        return BasicType.NULL;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NullField)) return false;
        if (!super.equals(o)) return false;
        NullField nullField = (NullField) o;
        return Objects.equals(getDefaultValue(), nullField.getDefaultValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDefaultValue());
    }
}
