package com.mqjd.datamodel.field;

import java.util.Map;

public class ObjectField extends BasicField {

  private Map<String, BasicField> properties;
  private Map<String, BasicField> patternProperties;
  private Boolean additionalProperties;
  private String[] required;
  private Integer minProperties;
  private Integer maxProperties;

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

  public Boolean getAdditionalProperties() {
    return additionalProperties;
  }

  public void setAdditionalProperties(Boolean additionalProperties) {
    this.additionalProperties = additionalProperties;
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
}
