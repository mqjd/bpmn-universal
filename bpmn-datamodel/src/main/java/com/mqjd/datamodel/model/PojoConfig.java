package com.mqjd.datamodel.model;

import com.mqjd.datamodel.schema.Schema;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class PojoConfig {
    private List<String> imports;
    private String className;
    private String packageName;
    private Schema schema;

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public String getClassName() {
        return className;
    }

    public String getFullClassName() {
        if (StringUtils.isNotBlank(getPackageName())) {
            return packageName + "." + className;
        }
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

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }
}
