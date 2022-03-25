package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;

public class IntegerField extends BasicField {

    private Long minimum;
    private Long maximum;
    private Long exclusiveMinimum;
    private Long exclusiveMaximum;
    private Long multipleOf;

    @JsonProperty("enum")
    private long[] enums;

    @JsonProperty("default")
    private Long defaultValue;

    @Override
    public BasicType getType() {
        return BasicType.INTEGER;
    }

    public Long getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Long defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Long getMinimum() {
        return minimum;
    }

    public void setMinimum(Long minimum) {
        this.minimum = minimum;
    }

    public Long getMaximum() {
        return maximum;
    }

    public void setMaximum(Long maximum) {
        this.maximum = maximum;
    }

    public Long getExclusiveMinimum() {
        return exclusiveMinimum;
    }

    public void setExclusiveMinimum(Long exclusiveMinimum) {
        this.exclusiveMinimum = exclusiveMinimum;
    }

    public Long getExclusiveMaximum() {
        return exclusiveMaximum;
    }

    public void setExclusiveMaximum(Long exclusiveMaximum) {
        this.exclusiveMaximum = exclusiveMaximum;
    }

    public Long getMultipleOf() {
        return multipleOf;
    }

    public void setMultipleOf(Long multipleOf) {
        this.multipleOf = multipleOf;
    }

    public long[] getEnums() {
        return enums;
    }

    public void setEnums(long[] enums) {
        this.enums = enums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerField)) return false;
        if (!super.equals(o)) return false;
        IntegerField that = (IntegerField) o;
        return Objects.equals(getMinimum(), that.getMinimum())
                && Objects.equals(getMaximum(), that.getMaximum())
                && Objects.equals(getExclusiveMinimum(), that.getExclusiveMinimum())
                && Objects.equals(getExclusiveMaximum(), that.getExclusiveMaximum())
                && Objects.equals(getMultipleOf(), that.getMultipleOf())
                && Arrays.equals(getEnums(), that.getEnums())
                && Objects.equals(getDefaultValue(), that.getDefaultValue());
    }

    @Override
    public int hashCode() {
        int result =
                Objects.hash(
                        super.hashCode(),
                        getMinimum(),
                        getMaximum(),
                        getExclusiveMinimum(),
                        getExclusiveMaximum(),
                        getMultipleOf(),
                        getDefaultValue());
        result = 31 * result + Arrays.hashCode(getEnums());
        return result;
    }
}
