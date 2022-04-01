package com.mqjd.datamodel.model;

import com.mqjd.datamodel.field.BasicField;
import com.mqjd.datamodel.field.BasicType;
import com.mqjd.datamodel.field.array.ArrayField;
import com.mqjd.datamodel.field.object.ObjectField;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.janino.JavaSourceClassLoader;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PojoFactory {
    private static final Logger LOG = LoggerFactory.getLogger(PojoFactory.class);
    private static final String POJO_TEMPLATE = "pojo.ftl";
    private static final Configuration cfg = new Configuration(Configuration.getVersion());

    static {
        cfg.setClassForTemplateLoading(PojoFactory.class, "/template");
    }

    public static <T> Class<T> createPojo(PojoConfig config, ClassLoader classLoader) {
        Pojo pojo = buildPojo(null, config.getSchema(), config);
        List<Pojo> pojoFields = split(pojo);
        pojoFields.add(0, pojo);
        Template template = getPojoTemplate();
        JavaSourceClassLoader sourceClassLoader =
                new JavaSourceClassLoader(
                        classLoader, new PojoResourceFinder(pojoFields, template), null);
        return getClass(config.fullClassName(), sourceClassLoader);
    }

    private static <T> Class<T> getClass(String className, ClassLoader classLoader) {
        try {
            //noinspection unchecked
            return (Class<T>) classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load class " + className, e);
        }
    }

    private static List<Pojo> split(Pojo pojo) {
        List<Field> fields = pojo.getFields();
        List<Pojo> pojoFields =
                fields.stream()
                        .filter(v -> v instanceof Pojo)
                        .map(v -> (Pojo) v)
                        .collect(Collectors.toList());
        List<Pojo> result = new ArrayList<>(pojoFields);
        for (Pojo pojoField : pojoFields) {
            result.addAll(split(pojoField));
        }
        return result;
    }

    private static Pojo buildPojo(String name, BasicField basicField, PojoConfig config) {
        Pojo.PojoBuilder builder = Pojo.newPojoBuilder();
        builder.className(config.newClassName());
        builder.packageName(config.getPackageName());
        builder.type(config.getPackageName() + "." + config.currentClassName());
        builder.name(name);
        ObjectField objectField = (ObjectField) basicField;
        objectField.getProperties().forEach((k, v) -> builder.addField(buildField(k, v, config)));
        return builder.build();
    }

    private static Field buildField(String fieldName, BasicField basicField, PojoConfig config) {
        BasicType type = basicField.getType();
        if (BasicType.OBJECT == type) {
            if (isPojoType(basicField)) {
                return buildPojo(fieldName, basicField, config);
            } else {
                Field.FieldBuilder builder = Field.newFieldBuilder().name(fieldName);
                builder.type(
                        String.format(
                                "%s<%s,%s>",
                                Map.class.getName(),
                                String.class.getName(),
                                Object.class.getName()));
                return builder.build();
            }
        } else if (BasicType.ARRAY == type) {
            ArrayField arrayField = (ArrayField) basicField;
            if (isPojoType(arrayField)) {
                Pojo.PojoBuilder builder = Pojo.newPojoBuilder().name(fieldName);
                Pojo pojo = buildPojo(null, arrayField.getItems(), config);
                builder.type(pojo.getClassName());
                builder.addField(pojo);
                return builder.build();
            } else {
                Field.FieldBuilder builder = Field.newFieldBuilder().name(fieldName);
                builder.type(
                        String.format(
                                "%s<%s>",
                                List.class.getName(),
                                getJavaType(arrayField.getItems().getType())));
                return builder.build();
            }
        } else {
            Field.FieldBuilder builder = Field.newFieldBuilder().name(fieldName);
            builder.type(getJavaType(basicField.getType()));
            return builder.build();
        }
    }

    private static String getJavaType(BasicType basicType) {
        switch (basicType) {
            case NUMBER:
                return Double.class.getName();
            case INTEGER:
                return Long.class.getName();
            case STRING:
                return String.class.getName();
            case BOOLEAN:
                return Boolean.class.getName();
            default:
                return Object.class.getName();
        }
    }

    private static boolean isPojoType(BasicField basicField) {
        if (basicField instanceof ObjectField) {
            ObjectField objectField = (ObjectField) basicField;
            boolean notEmptyProperties = ObjectUtils.isNotEmpty(objectField.getProperties());
            boolean emptyPatternProperties =
                    ObjectUtils.isEmpty(objectField.getPatternProperties());
            boolean noAdditionalProperties =
                    ObjectUtils.isEmpty(objectField.getAdditionalProperties());
            boolean emptyAllOf = ObjectUtils.isEmpty(objectField.getAllOf());
            boolean emptyAnyOf = ObjectUtils.isEmpty(objectField.getAnyOf());
            boolean emptyOneOf = ObjectUtils.isEmpty(objectField.getOneOf());
            return notEmptyProperties
                    && emptyPatternProperties
                    && noAdditionalProperties
                    && emptyAllOf
                    && emptyAnyOf
                    && emptyOneOf;
        } else if (basicField instanceof ArrayField) {
            ArrayField arrayField = (ArrayField) basicField;
            return arrayField.getItems() != null && isPojoType(arrayField.getItems());
        } else {
            return false;
        }
    }

    private static String processTemplate(Template template, Pojo pojo) {
        try {
            StringWriter stringWriter = new StringWriter();
            template.process(pojo, stringWriter);
            return stringWriter.toString();
        } catch (TemplateException | IOException e) {
            throw new IllegalArgumentException("process template failed", e);
        }
    }

    private static Template getPojoTemplate() {
        try {
            return cfg.getTemplate(POJO_TEMPLATE);
        } catch (IOException e) {
            throw new IllegalArgumentException("read pojo.ftl failed", e);
        }
    }
}
