package com.mqjd.datamodel.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.mqjd.datamodel.field.BasicField;
import com.mqjd.datamodel.field.object.ObjectField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonSubTypes({@JsonSubTypes.Type(value = Schema.class, name = "object")})
public class Schema extends ObjectField {
    @JsonProperty("$id")
    private String id;

    @JsonProperty("meta:class")
    private Class metaClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class getMetaClass() {
        return metaClass;
    }

    public void setMetaClass(Class metaClass) {
        this.metaClass = metaClass;
    }

    public void addField(BasicField field) {
        if (getProperties() == null) {
            setProperties(new HashMap<>());
        }
        getProperties().put(field.getTitle(), field);
    }

    @JsonIgnore
    public List<BasicField> getFields() {
        return new ArrayList<>(getProperties().values());
    }
}
