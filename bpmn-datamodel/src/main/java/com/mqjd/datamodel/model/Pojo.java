package com.mqjd.datamodel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pojo extends Field {
    private List<String> imports;
    private String className;
    private String packageName;
    private List<Field> fields;

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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String type;
        private final Set<String> imports;
        private String className;
        private String packageName;
        private final List<Field> fields;

        private Builder() {
            this.fields = new ArrayList<>();
            this.imports = new HashSet<>();
        }

        public static Builder aPojo() {
            return new Builder();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder addImport(String classImport) {
            this.imports.add(classImport);
            return this;
        }

        public Builder className(String className) {
            this.className = className;
            return this;
        }

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder addField(Field field) {
            this.fields.add(field);
            return this;
        }

        public Pojo build() {
            Pojo pojo = new Pojo();
            pojo.setName(name);
            pojo.setType(type);
            pojo.setClassName(className);
            pojo.setPackageName(packageName);
            pojo.setFields(fields);
            return pojo;
        }
    }
}
