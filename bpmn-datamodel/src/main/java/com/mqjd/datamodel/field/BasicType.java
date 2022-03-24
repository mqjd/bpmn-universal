package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum BasicType {
  STRING,
  LONG,
  DOUBLE,
  DATE,
  DATETIME,
  OBJECT,
  ARRAY;

  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  public static BasicType fromString(String key) {
    BasicType[] values = values();
    for (BasicType value : values) {
      if (value.name().toLowerCase(Locale.ROOT).equals(key)) {
        return value;
      }
    }
    return null;
  }

  @JsonValue
  public String getString() {
    return this.name().toLowerCase(Locale.ROOT);
  }
}
