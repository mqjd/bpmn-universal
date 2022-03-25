package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;

public class StringField extends BasicField {

    private Integer minLength;
    private Integer maxLength;
    private String pattern;
    private String format;

    @JsonProperty("enum")
    private String[] enums;

    @JsonProperty("default")
    private String defaultValue;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public BasicType getType() {
        return BasicType.STRING;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String[] getEnums() {
        return enums;
    }

    public void setEnums(String[] enums) {
        this.enums = enums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringField)) return false;
        if (!super.equals(o)) return false;
        StringField that = (StringField) o;
        return Objects.equals(getMinLength(), that.getMinLength())
                && Objects.equals(getMaxLength(), that.getMaxLength())
                && Objects.equals(getPattern(), that.getPattern())
                && Objects.equals(getFormat(), that.getFormat())
                && Arrays.equals(getEnums(), that.getEnums())
                && Objects.equals(getDefaultValue(), that.getDefaultValue());
    }

    @Override
    public int hashCode() {
        int result =
                Objects.hash(
                        super.hashCode(),
                        getMinLength(),
                        getMaxLength(),
                        getPattern(),
                        getFormat(),
                        getDefaultValue());
        result = 31 * result + Arrays.hashCode(getEnums());
        return result;
    }
}
