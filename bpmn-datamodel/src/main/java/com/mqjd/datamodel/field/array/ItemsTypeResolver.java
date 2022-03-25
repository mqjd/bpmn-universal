package com.mqjd.datamodel.field.array;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.mqjd.datamodel.field.BasicField;
import com.mqjd.datamodel.field.object.AdditionalPropertiesTypeResolver;

public class ItemsTypeResolver extends AdditionalPropertiesTypeResolver {
    private static final NamedType nullNamedType = new NamedType(BasicField.class, null);

    @Override
    protected NamedType getNullNamedType() {
        return nullNamedType;
    }
}
