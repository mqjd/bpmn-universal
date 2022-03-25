package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class BooleanField extends BasicField {

    @JsonProperty("default")
    private Boolean defaultValue;

    @Override
    public BasicType getType() {
        return BasicType.BOOLEAN;
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanField)) return false;
        if (!super.equals(o)) return false;
        BooleanField that = (BooleanField) o;
        return Objects.equals(getDefaultValue(), that.getDefaultValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDefaultValue());
    }
}
