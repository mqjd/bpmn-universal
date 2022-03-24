package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = StringField.class, name = "string"),
  @JsonSubTypes.Type(value = LongField.class, name = "long"),
  @JsonSubTypes.Type(value = ObjectField.class, name = "object")
})
public class BasicField {
  private String id;
  private String title;
  private String description;
  private BasicType type;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BasicType getType() {
    return type;
  }

  public void setType(BasicType type) {
    this.type = type;
  }
}
