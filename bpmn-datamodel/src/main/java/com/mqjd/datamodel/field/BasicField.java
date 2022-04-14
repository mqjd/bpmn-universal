package com.mqjd.datamodel.field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mqjd.datamodel.field.array.ArrayField;
import com.mqjd.datamodel.field.object.ObjectField;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class BasicField {

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
        Set<BasicType> basicTypes =
                Stream.of(collectTypes(allOf), collectTypes(anyOf), collectTypes(oneOf))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
        return basicTypes.size() == 1 ? basicTypes.stream().findFirst().get() : BasicType.ANY;
    }

    public Set<BasicType> collectTypes(BasicField[] fields) {
        return Optional.ofNullable(fields)
                .map(v -> Arrays.stream(v).map(BasicField::getType).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicField that = (BasicField) o;
        return Objects.equals(defs, that.defs)
                && Objects.equals(ref, that.ref)
                && Objects.equals(title, that.title)
                && Objects.equals(description, that.description)
                && Arrays.equals(allOf, that.allOf)
                && Arrays.equals(anyOf, that.anyOf)
                && Arrays.equals(oneOf, that.oneOf)
                && Objects.equals(not, that.not)
                && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(defs, ref, title, description, not, type);
        result = 31 * result + Arrays.hashCode(allOf);
        result = 31 * result + Arrays.hashCode(anyOf);
        result = 31 * result + Arrays.hashCode(oneOf);
        return result;
    }
}
