package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StringField extends BasicField {

  private Integer minLength;
  private Integer maxLength;
  private String pattern;
  private String format;

  @JsonProperty("enum")
  private String[] enums;

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
}
