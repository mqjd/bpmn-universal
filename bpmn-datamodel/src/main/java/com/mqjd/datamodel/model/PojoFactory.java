package com.mqjd.datamodel.model;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.SimpleCompiler;

import java.io.IOException;
import java.io.StringWriter;

public class PojoFactory {
    private static final String POJO_TEMPLATE = "pojo.ftl";
    private static final Configuration cfg = new Configuration(Configuration.getVersion());

    static {
        cfg.setClassForTemplateLoading(PojoFactory.class, "/template");
    }

    public static <T> Class<T> createPojo(PojoConfig config, ClassLoader classLoader) {
        String code = processTemplate(getPojoTemplate(), config);
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.setParentClassLoader(classLoader);
        try {
            compiler.cook(code);
        } catch (CompileException e) {
            throw new IllegalArgumentException("the program can not be compiled", e);
        }
        try {
            //noinspection unchecked
            return (Class<T>) compiler.getClassLoader().loadClass(config.getFullClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load class " + config.getFullClassName(), e);
        }
    }

    private static String processTemplate(Template template, PojoConfig config) {
        try {
            StringWriter stringWriter = new StringWriter();
            template.process(config, stringWriter);
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
