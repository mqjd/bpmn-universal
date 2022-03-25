package com.mqjd.datamodel.field.array;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.mqjd.datamodel.field.BasicField;
import com.mqjd.datamodel.field.BasicType;

import java.util.Arrays;
import java.util.Objects;

public class ArrayField extends BasicField {
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonTypeResolver(ItemsTypeResolver.class)
    private Object items;

    private BasicField contains;
    private Integer minContains;
    private Integer maxContains;
    private Integer minItems;
    private Integer maxItems;
    private Boolean uniqueItems;

    private BasicField[] prefixItems;

    @Override
    public BasicType getType() {
        return BasicType.ARRAY;
    }

    public Object getItems() {
        return items;
    }

    public void setItems(Object items) {
        this.items = items;
    }

    public BasicField getContains() {
        return contains;
    }

    public void setContains(BasicField contains) {
        this.contains = contains;
    }

    public Integer getMinContains() {
        return minContains;
    }

    public void setMinContains(Integer minContains) {
        this.minContains = minContains;
    }

    public Integer getMaxContains() {
        return maxContains;
    }

    public void setMaxContains(Integer maxContains) {
        this.maxContains = maxContains;
    }

    public Integer getMinItems() {
        return minItems;
    }

    public void setMinItems(Integer minItems) {
        this.minItems = minItems;
    }

    public Integer getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(Integer maxItems) {
        this.maxItems = maxItems;
    }

    public Boolean getUniqueItems() {
        return uniqueItems;
    }

    public void setUniqueItems(Boolean uniqueItems) {
        this.uniqueItems = uniqueItems;
    }

    public BasicField[] getPrefixItems() {
        return prefixItems;
    }

    public void setPrefixItems(BasicField[] prefixItems) {
        this.prefixItems = prefixItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayField)) return false;
        if (!super.equals(o)) return false;
        ArrayField that = (ArrayField) o;
        return Objects.equals(getItems(), that.getItems())
                && Objects.equals(getContains(), that.getContains())
                && Objects.equals(getMinContains(), that.getMinContains())
                && Objects.equals(getMaxContains(), that.getMaxContains())
                && Objects.equals(getMinItems(), that.getMinItems())
                && Objects.equals(getMaxItems(), that.getMaxItems())
                && Objects.equals(getUniqueItems(), that.getUniqueItems())
                && Arrays.equals(getPrefixItems(), that.getPrefixItems());
    }

    @Override
    public int hashCode() {
        int result =
                Objects.hash(
                        super.hashCode(),
                        getItems(),
                        getContains(),
                        getMinContains(),
                        getMaxContains(),
                        getMinItems(),
                        getMaxItems(),
                        getUniqueItems());
        result = 31 * result + Arrays.hashCode(getPrefixItems());
        return result;
    }
}
