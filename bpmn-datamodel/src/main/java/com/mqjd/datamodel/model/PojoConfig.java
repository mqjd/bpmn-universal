package com.mqjd.datamodel.model;

import com.mqjd.datamodel.schema.Schema;
import com.mqjd.datamodel.utils.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class PojoConfig {
    private final AtomicInteger atomicInteger = new AtomicInteger(-1);
    private String className;
    private String packageName;
    private Schema schema;

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

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public String newClassName() {
        int index = atomicInteger.incrementAndGet();
        if (index == 0) {
            return className;
        }
        return String.format("%s%s", className, index);
    }

    public String currentClassName() {
        int index = atomicInteger.get();
        if (index == 0) {
            return className;
        }
        return String.format("%s%s", className, index);
    }

    public String fullClassName() {
        if (StringUtils.isBlank(packageName)) {
            return className;
        }
        return String.format("%s.%s", packageName, className);
    }
}
