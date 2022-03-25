package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;

public class NumberField extends BasicField {
    private Double minimum;
    private Double maximum;
    private Double exclusiveMinimum;
    private Double exclusiveMaximum;
    private Double multipleOf;

    @JsonProperty("enum")
    private double[] enums;

    @JsonProperty("default")
    private Double defaultValue;

    @Override
    public BasicType getType() {
        return BasicType.NUMBER;
    }

    public Double getMinimum() {
        return minimum;
    }

    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }

    public Double getMaximum() {
        return maximum;
    }

    public void setMaximum(Double maximum) {
        this.maximum = maximum;
    }

    public Double getExclusiveMinimum() {
        return exclusiveMinimum;
    }

    public void setExclusiveMinimum(Double exclusiveMinimum) {
        this.exclusiveMinimum = exclusiveMinimum;
    }

    public Double getExclusiveMaximum() {
        return exclusiveMaximum;
    }

    public void setExclusiveMaximum(Double exclusiveMaximum) {
        this.exclusiveMaximum = exclusiveMaximum;
    }

    public Double getMultipleOf() {
        return multipleOf;
    }

    public void setMultipleOf(Double multipleOf) {
        this.multipleOf = multipleOf;
    }

    public double[] getEnums() {
        return enums;
    }

    public void setEnums(double[] enums) {
        this.enums = enums;
    }

    public Double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Double defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberField)) return false;
        if (!super.equals(o)) return false;
        NumberField that = (NumberField) o;
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
