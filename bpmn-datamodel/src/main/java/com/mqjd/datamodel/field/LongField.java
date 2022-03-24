package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LongField extends BasicField {

  private Long minimum;
  private Long maximum;
  private Long exclusiveMinimum;
  private Long exclusiveMaximum;
  private Long multipleOf;
  @JsonProperty("enum")
  private long[] enums;
  @Override
  public BasicType getType() {
    return BasicType.LONG;
  }
}
