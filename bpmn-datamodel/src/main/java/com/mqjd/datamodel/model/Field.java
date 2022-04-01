package com.mqjd.datamodel.model;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private String name;
    private String type;
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

    public static FieldBuilder newFieldBuilder() {
        return new FieldBuilder();
    }

    public static final class FieldBuilder {
        private String name;
        private String type;
        private final List<String> imports = new ArrayList<>();

        public FieldBuilder name(String name) {
            this.name = name;
            return this;
        }

        public FieldBuilder type(String type) {
            this.type = type;
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
            field.setImports(imports);
            return field;
        }
    }
}
