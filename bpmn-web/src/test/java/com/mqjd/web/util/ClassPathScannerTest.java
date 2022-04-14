package com.mqjd.web.util;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.IOException;

public class ClassPathScannerTest {
    @Test
    public void test() throws ClassNotFoundException, IOException {
        String className = "Test";
        String fieldName = "a";
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classWriter.visit(
                Opcodes.V1_8,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,
                className,
                null,
                "java/lang/Object",
                null);
        classWriter.visitSource(className + ".java", null);
        generatedInit(classWriter);
        classWriter.visitField(Opcodes.ACC_PUBLIC, fieldName, "Ljava/lang/String;", null, null);
        generatedGet(classWriter, fieldName, className);
        classWriter.visitEnd();
        byte[] bytes = classWriter.toByteArray();
        FileUtils.writeByteArrayToFile(new File("a.class"), bytes, false);
    }

    private void generatedInit(ClassWriter cw) {
        // 构造函数
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        Label initLabel = new Label();
        mv.visitLabel(initLabel);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitLineNumber(15, initLabel);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    private void generatedGet(ClassWriter cw, String name, String className) {
        // 生成get函数，不接收参数，有返回值
        MethodVisitor mv =
                cw.visitMethod(
                        Opcodes.ACC_PUBLIC,
                        "get" + firstLetterToUpperCase(name),
                        "()Ljava/lang/String;",
                        null,
                        null);
        mv.visitCode();
        Label label = new Label();
        mv.visitLabel(label);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(Opcodes.GETFIELD, className, name, "Ljava/lang/String;");
        mv.visitInsn(Opcodes.ARETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    private String firstLetterToUpperCase(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= (cs[0] > 96 && cs[0] < 123) ? 32 : 0;
        return String.valueOf(cs);
    }
}
