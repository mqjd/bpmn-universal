package com.mqjd.datamodel.model;

import com.mqjd.datamodel.field.BasicField;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private String name;
    private String type;
    private String fullType;
    private BasicField field;
    private List<String> imports;

    public String getName() {
        return name;
    }

    public String getFieldName() {
        return "$" + name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public String getFullType() {
        return fullType;
    }

    public void setFullType(String fullType) {
        this.fullType = fullType;
    }

    public BasicField getField() {
        return field;
    }

    public void setField(BasicField field) {
        this.field = field;
    }

    public static FieldBuilder newFieldBuilder() {
        return new FieldBuilder();
    }

    public static class FieldBuilder {
        private String name;
        private String type;
        private String fullType;
        private BasicField field;
        private final List<String> imports = new ArrayList<>();

        public FieldBuilder name(String name) {
            this.name = name;
            return this;
        }

        public FieldBuilder field(BasicField field) {
            this.field = field;
            return this;
        }

        public FieldBuilder type(String type) {
            this.type = type;
            return this;
        }

        public FieldBuilder fullType(String fullType) {
            this.fullType = fullType;
            return this;
        }

        public FieldBuilder addImport(String fullClassName) {
            this.imports.add(fullClassName);
            return this;
        }

        public Field build() {
            Field field = new Field();
            field.setName(name);
            field.setType(type);
            field.setFullType(fullType);
            field.setField(this.field);
            field.setImports(imports);
            return field;
        }
    }
}
