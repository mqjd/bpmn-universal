package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.*;
import com.mqjd.datamodel.field.array.ArrayField;
import com.mqjd.datamodel.field.object.ObjectField;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = StringField.class, name = "string"),
    @JsonSubTypes.Type(value = IntegerField.class, name = "integer"),
    @JsonSubTypes.Type(value = NumberField.class, name = "number"),
    @JsonSubTypes.Type(value = ArrayField.class, name = "array"),
    @JsonSubTypes.Type(value = BooleanField.class, name = "boolean"),
    @JsonSubTypes.Type(value = ObjectField.class, name = "object"),
    @JsonSubTypes.Type(value = BasicField.class, name = "any"),
    @JsonSubTypes.Type(value = NullField.class, name = "null")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicField {
    @JsonProperty("$id")
    private String id;

    @JsonProperty("$defs")
    private Map<String, BasicField> defs;

    @JsonProperty("$ref")
    private String ref;

    private String title;
    private String description;
    private BasicField[] allOf;
    private BasicField[] anyOf;
    private BasicField[] oneOf;
    private BasicField not;
    private BasicType type;

    public void setType(BasicType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, BasicField> getDefs() {
        return defs;
    }

    public void setDefs(Map<String, BasicField> defs) {
        this.defs = defs;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
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

    public BasicField[] getAllOf() {
        return allOf;
    }

    public void setAllOf(BasicField[] allOf) {
        this.allOf = allOf;
    }

    public BasicField[] getAnyOf() {
        return anyOf;
    }

    public void setAnyOf(BasicField[] anyOf) {
        this.anyOf = anyOf;
    }

    public BasicField[] getOneOf() {
        return oneOf;
    }

    public void setOneOf(BasicField[] oneOf) {
        this.oneOf = oneOf;
    }

    public BasicField getNot() {
        return not;
    }

    public void setNot(BasicField not) {
        this.not = not;
    }

    @JsonIgnore
    public BasicType getType() {
        return type == null ? BasicType.ANY : type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicField)) return false;
        BasicField that = (BasicField) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getDefs(), that.getDefs())
                && Objects.equals(getRef(), that.getRef())
                && Objects.equals(getTitle(), that.getTitle())
                && Objects.equals(getDescription(), that.getDescription())
                && Arrays.equals(getAllOf(), that.getAllOf())
                && Arrays.equals(getAnyOf(), that.getAnyOf())
                && Arrays.equals(getOneOf(), that.getOneOf())
                && Objects.equals(getNot(), that.getNot());
    }

    @Override
    public int hashCode() {
        int result =
                Objects.hash(getId(), getDefs(), getRef(), getTitle(), getDescription(), getNot());
        result = 31 * result + Arrays.hashCode(getAllOf());
        result = 31 * result + Arrays.hashCode(getAnyOf());
        result = 31 * result + Arrays.hashCode(getOneOf());
        return result;
    }
}
