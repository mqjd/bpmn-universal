package com.mqjd.datamodel.model;

import com.mqjd.datamodel.field.BasicField;

import java.util.ArrayList;
import java.util.List;

public class Pojo extends Field {

    private String className;
    private String packageName;
    private List<Field> fields;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getFullClassName() {
        return packageName + "." + className;
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

    public static PojoBuilder newPojoBuilder() {
        return new PojoBuilder();
    }

    public static final class PojoBuilder extends  FieldBuilder{
        private String name;
        private String type;
        private final List<String> imports = new ArrayList<>();
        private String className;
        private String packageName;
        private String fullType;
        private BasicField schema;
        private final List<Field> fields = new ArrayList<>();

        public PojoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PojoBuilder type(String type) {
            this.type = type;
            return this;
        }

        public PojoBuilder schema(BasicField schema) {
            this.schema = schema;
            return this;
        }

        public PojoBuilder fullType(String fullType) {
            this.fullType = fullType;
            return this;
        }

        public PojoBuilder addImport(String fullClassName) {
            this.imports.add(fullClassName);
            return this;
        }

        public PojoBuilder className(String className) {
            this.className = className;
            return this;
        }

        public PojoBuilder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public PojoBuilder addField(Field field) {
            this.fields.add(field);
            return this;
        }

        public Pojo build() {
            Pojo pojo = new Pojo();
            pojo.setName(name);
            pojo.setType(type);
            pojo.setFullType(fullType);
            pojo.setImports(imports);
            pojo.setClassName(className);
            pojo.setPackageName(packageName);
            pojo.setSchema(schema);
            pojo.setFields(fields);
            return pojo;
        }
    }
}
