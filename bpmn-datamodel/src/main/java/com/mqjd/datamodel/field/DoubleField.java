package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DoubleField extends BasicField {
  private Double minimum;
  private Double maximum;
  private Double exclusiveMinimum;
  private Double exclusiveMaximum;
  private Double multipleOf;

  @JsonProperty("enum")
  private double[] enums;

  @Override
  public BasicType getType() {
    return BasicType.DOUBLE;
  }
}
