package com.mqjd.datamodel.codegen;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mqjd.datamodel.model.Field;
import com.mqjd.datamodel.model.Pojo;
import org.objectweb.asm.*;

public class ClassGenFactory extends ClassLoader implements Opcodes {
    private static final String JSON_PROPERTY_ANNOTATION_CLASS =
            genClassDescriptor(JsonProperty.class);

    public static void genPojo(Pojo pojo, ClassLoader classLoader) {
        ClassWriter classWriter = new ClassWriter(0);
    }

    private static void createClass(Pojo pojo, ClassWriter classWriter) {
        classWriter.visit(
                V1_8, ACC_PUBLIC + ACC_SUPER, genClassName(pojo), null, "java/lang/Object", null);
        classWriter.visitSource(genJavaName(pojo), null);
    }

    private static void createField(Field field, ClassWriter classWriter) {
        FieldVisitor fieldVisitor =
                classWriter.visitField(ACC_PRIVATE, "ref", "Ljava/lang/String;", null, null);
        AnnotationVisitor annotationVisitor =
                fieldVisitor.visitAnnotation(JSON_PROPERTY_ANNOTATION_CLASS, true);
        annotationVisitor.visit(field.getFieldName(), field.getName());
        annotationVisitor.visitEnd();
        fieldVisitor.visitEnd();
    }

    private static void createInit(Field field, ClassWriter classWriter) {
        MethodVisitor methodVisitor =
                classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        Label initLabel = new Label();
        methodVisitor.visitLabel(initLabel);
        methodVisitor.visitLineNumber(5, initLabel);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    private static String genClassDescriptor(Class<?> clz) {
        return String.format("L%s;", clz.getName()).replaceAll("[.]", "//");
    }

    private static String genClassName(Pojo pojo) {
        return (pojo.getPackageName() + "." + pojo.getClassName()).replaceAll("//{2,}", "/");
    }

    private static String genJavaName(Pojo pojo) {
        return pojo.getPackageName() + "." + pojo.getClassName();
    }
}
