package com.mqjd.datamodel.field.object;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.mqjd.datamodel.field.BasicField;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AdditionalPropertiesTypeResolver extends StdTypeResolverBuilder {
    private static final NamedType nullNamedType = new NamedType(Boolean.class, null);

    @Override
    public TypeDeserializer buildTypeDeserializer(
            DeserializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
        return super.buildTypeDeserializer(
                config, baseType, getNamedTypes(config, getNullNamedType()));
    }

    @Override
    public TypeSerializer buildTypeSerializer(
            SerializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
        return super.buildTypeSerializer(
                config, baseType, getNamedTypes(config, getNullNamedType()));
    }

    protected NamedType getNullNamedType() {
        return nullNamedType;
    }

    private List<NamedType> getNamedTypes(MapperConfig<?> config, NamedType namedType) {
        AnnotatedClass annotatedClass =
                AnnotatedClassResolver.resolveWithoutSuperTypes(config, BasicField.class);
        return config
                .getSubtypeResolver()
                .collectAndResolveSubtypesByClass(config, annotatedClass)
                .stream()
                .map(
                        v -> {
                            if (v.getName() == null) {
                                return namedType;
                            } else {
                                return v;
                            }
                        })
                .collect(Collectors.toList());
    }
}
