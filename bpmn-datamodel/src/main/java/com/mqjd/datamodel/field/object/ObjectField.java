package com.mqjd.datamodel.field.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.mqjd.datamodel.field.BasicField;
import com.mqjd.datamodel.field.BasicType;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class ObjectField extends BasicField {

    private Map<String, BasicField> properties;
    private Map<String, BasicField> patternProperties;
    private String[] required;
    private Integer minProperties;
    private Integer maxProperties;

    @JsonProperty("if")
    private ObjectField when;

    @JsonProperty("then")
    private ObjectField then;

    @JsonProperty("else")
    private ObjectField other;

    private Map<String, String[]> dependentRequired;
    private Map<String, BasicField> dependentSchemas;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonTypeResolver(AdditionalPropertiesTypeResolver.class)
    private Object additionalProperties;

    private PropertyNames propertyNames;

    @Override
    public BasicType getType() {
        return BasicType.OBJECT;
    }

    public Map<String, BasicField> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, BasicField> properties) {
        this.properties = properties;
    }

    public Map<String, BasicField> getPatternProperties() {
        return patternProperties;
    }

    public void setPatternProperties(Map<String, BasicField> patternProperties) {
        this.patternProperties = patternProperties;
    }

    public String[] getRequired() {
        return required;
    }

    public void setRequired(String[] required) {
        this.required = required;
    }

    public Integer getMinProperties() {
        return minProperties;
    }

    public void setMinProperties(Integer minProperties) {
        this.minProperties = minProperties;
    }

    public Integer getMaxProperties() {
        return maxProperties;
    }

    public void setMaxProperties(Integer maxProperties) {
        this.maxProperties = maxProperties;
    }

    public ObjectField getWhen() {
        return when;
    }

    public void setWhen(ObjectField when) {
        this.when = when;
    }

    public ObjectField getThen() {
        return then;
    }

    public void setThen(ObjectField then) {
        this.then = then;
    }

    public ObjectField getOther() {
        return other;
    }

    public void setOther(ObjectField other) {
        this.other = other;
    }

    public Map<String, String[]> getDependentRequired() {
        return dependentRequired;
    }

    public void setDependentRequired(Map<String, String[]> dependentRequired) {
        this.dependentRequired = dependentRequired;
    }

    public Map<String, BasicField> getDependentSchemas() {
        return dependentSchemas;
    }

    public void setDependentSchemas(Map<String, BasicField> dependentSchemas) {
        this.dependentSchemas = dependentSchemas;
    }

    public Object getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Object additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public PropertyNames getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(PropertyNames propertyNames) {
        this.propertyNames = propertyNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectField)) return false;
        if (!super.equals(o)) return false;
        ObjectField that = (ObjectField) o;
        return Objects.equals(getProperties(), that.getProperties())
                && Objects.equals(getPatternProperties(), that.getPatternProperties())
                && Arrays.equals(getRequired(), that.getRequired())
                && Objects.equals(getMinProperties(), that.getMinProperties())
                && Objects.equals(getMaxProperties(), that.getMaxProperties())
                && Objects.equals(getWhen(), that.getWhen())
                && Objects.equals(getThen(), that.getThen())
                && Objects.equals(getOther(), that.getOther())
                && Objects.equals(getDependentRequired(), that.getDependentRequired())
                && Objects.equals(getDependentSchemas(), that.getDependentSchemas())
                && Objects.equals(getAdditionalProperties(), that.getAdditionalProperties())
                && Objects.equals(getPropertyNames(), that.getPropertyNames());
    }

    @Override
    public int hashCode() {
        int result =
                Objects.hash(
                        super.hashCode(),
                        getProperties(),
                        getPatternProperties(),
                        getMinProperties(),
                        getMaxProperties(),
                        getWhen(),
                        getThen(),
                        getOther(),
                        getDependentRequired(),
                        getDependentSchemas(),
                        getAdditionalProperties(),
                        getPropertyNames());
        result = 31 * result + Arrays.hashCode(getRequired());
        return result;
    }
}
