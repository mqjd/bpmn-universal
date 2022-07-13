package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class StringField extends BasicField {

    private Integer minLength;
    private Integer maxLength;
    private String pattern;
    private String format;

    @JsonProperty("enum")
    private List<String> enums;

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

    public List<String> getEnums() {
        return enums;
    }

    public void setEnums(List<String> enums) {
        this.enums = enums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        StringField that = (StringField) o;
        return Objects.equals(minLength, that.minLength) && Objects.equals(maxLength, that.maxLength) && Objects.equals(
                pattern, that.pattern) && Objects.equals(format, that.format) && Objects.equals(enums,
                                                                                                that.enums
        ) && Objects.equals(defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minLength, maxLength, pattern, format, enums, defaultValue);
    }
}
