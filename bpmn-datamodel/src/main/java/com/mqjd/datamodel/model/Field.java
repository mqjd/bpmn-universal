package com.mqjd.datamodel.model;

public class Field {
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String type;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Field build() {
            Field field = new Field();
            field.name = this.name;
            field.type = this.type;
            return field;
        }
    }
}
