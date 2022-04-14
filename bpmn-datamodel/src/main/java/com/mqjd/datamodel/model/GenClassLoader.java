package com.mqjd.datamodel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mqjd.datamodel.field.BasicField;
import com.mqjd.datamodel.field.BasicType;
import com.mqjd.datamodel.field.array.ArrayField;
import com.mqjd.datamodel.field.object.ObjectField;
import com.mqjd.datamodel.utils.ObjectUtils;
import org.objectweb.asm.*;

import java.util.*;
import java.util.stream.Collectors;

public class GenClassLoader extends ClassLoader implements Opcodes {
    private static final String JSON_PROPERTY_ANNOTATION_CLASS =
            getFieldDescriptor(JsonProperty.class);

    private static final String JSON_PROPERTY_ANNOTATION_VALUE_NAME = "value";

    private static final String DEFAULT_SUPER_CLASS_NAME = "java/lang/Object";
    private static final String CONSTRUCTOR_METHOD_NAME = "<init>";
    private static final String CONSTRUCTOR_METHOD_DESCRIPTOR = "()V";
    private final Map<String, Class<?>> cachedClass = new HashMap<>();

    public GenClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (cachedClass.containsKey(name)) {
            return cachedClass.get(name);
        }
        return super.findClass(name);
    }

    public <T> Class<T> genPojo(PojoConfig config) {
        Pojo pojo = buildPojo(null, config.getSchema(), config);
        List<Pojo> pojoFields = split(pojo);
        pojoFields.add(0, pojo);
        Collections.reverse(pojoFields);
        pojoFields.forEach(this::genClass);
        return getClass(config.fullClassName(), this);
    }

    private void genClass(Pojo pojo) {
        cachedClass.computeIfAbsent(pojo.getFullClassName(), key -> genPojo(pojo));
    }

    private Class<?> genPojo(Pojo pojo) {
        ClassWriter classWriter = new ClassWriter(0);
        createClass(pojo, classWriter);
        createInit(classWriter);
        for (Field field : pojo.getFields()) {
            createField(field, classWriter);
        }
        classWriter.visitEnd();
        byte[] bytes = classWriter.toByteArray();
        return defineClass(bytes);
    }

    private Class<?> defineClass(byte[] bytes) {
        return super.defineClass(null, bytes, 0, bytes.length);
    }

    private static void createClass(Pojo pojo, ClassWriter classWriter) {
        classWriter.visit(
                V1_8,
                ACC_PUBLIC + ACC_SUPER,
                getClassDescriptor(pojo),
                null,
                DEFAULT_SUPER_CLASS_NAME,
                null);
        classWriter.visitSource(genJavaName(pojo), null);
    }

    private static void createField(Field field, ClassWriter classWriter) {
        FieldVisitor fieldVisitor =
                classWriter.visitField(
                        ACC_PUBLIC,
                        field.getFieldName(),
                        getFieldDescriptor(field.getType()),
                        field.getFullType(),
                        null);
        AnnotationVisitor annotationVisitor =
                fieldVisitor.visitAnnotation(JSON_PROPERTY_ANNOTATION_CLASS, true);
        annotationVisitor.visit(JSON_PROPERTY_ANNOTATION_VALUE_NAME, field.getName());
        annotationVisitor.visitEnd();
        fieldVisitor.visitEnd();
    }

    private static void createInit(ClassWriter classWriter) {
        MethodVisitor methodVisitor =
                classWriter.visitMethod(
                        ACC_PUBLIC,
                        CONSTRUCTOR_METHOD_NAME,
                        CONSTRUCTOR_METHOD_DESCRIPTOR,
                        null,
                        null);
        methodVisitor.visitCode();
        Label initLabel = new Label();
        methodVisitor.visitLabel(initLabel);
        methodVisitor.visitLineNumber(5, initLabel);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(
                INVOKESPECIAL,
                DEFAULT_SUPER_CLASS_NAME,
                CONSTRUCTOR_METHOD_NAME,
                CONSTRUCTOR_METHOD_DESCRIPTOR,
                false);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    private static String getFieldDescriptor(Class<?> clz) {
        return getFieldDescriptor(clz.getName());
    }

    private static String getFieldDescriptor(String fullClassName) {
        return String.format("L%s;", fullClassName).replaceAll("\\.+", "/");
    }

    private static String getAnnotationFieldDescriptor(String fullClassName) {
        return String.format("L%s", fullClassName).replaceAll("\\.+", "/");
    }

    private static String getClassDescriptor(Pojo pojo) {
        return pojo.getFullClassName().replaceAll("\\.+", "/");
    }

    private static String genJavaName(Pojo pojo) {
        return pojo.getClassName() + ".java";
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
                builder.type(Map.class.getName())
                        .fullType(
                                String.format(
                                        "%s<%s,%s>;",
                                        getAnnotationFieldDescriptor(Map.class.getName()),
                                        getFieldDescriptor(String.class.getName()),
                                        getFieldDescriptor(Object.class.getName())));
                return builder.build();
            }
        } else if (BasicType.ARRAY == type) {
            ArrayField arrayField = (ArrayField) basicField;
            if (isPojoType(arrayField)) {
                Pojo.PojoBuilder builder = Pojo.newPojoBuilder().name(fieldName);
                Pojo pojo = buildPojo(null, arrayField.getItems(), config);
                builder.type(pojo.getFullClassName())
                        .fullType(
                                String.format(
                                        "%s<%s>;",
                                        getAnnotationFieldDescriptor(List.class.getName()),
                                        getFieldDescriptor(pojo.getFullClassName())));

                builder.addField(pojo);
                return builder.build();
            } else {
                Field.FieldBuilder builder = Field.newFieldBuilder().name(fieldName);
                builder.type(List.class.getName())
                        .fullType(
                                String.format(
                                        "%s<%s>;",
                                        getAnnotationFieldDescriptor(List.class.getName()),
                                        getFieldDescriptor(
                                                getJavaType(arrayField.getItems().getType()))));
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
}
