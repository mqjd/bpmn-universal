package com.mqjd.datamodel.model;

import com.mqjd.datamodel.field.BasicField;

import java.util.List;

public class PojoConfig {
    private List<String> imports;
    private String className;
    private List<BasicField> fields;

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<BasicField> getFields() {
        return fields;
    }

    public void setFields(List<BasicField> fields) {
        this.fields = fields;
    }
}
