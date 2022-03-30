package com.mqjd.datamodel.model;

import com.mqjd.datamodel.field.BasicField;
import com.mqjd.datamodel.field.BasicType;
import com.mqjd.datamodel.field.array.ArrayField;
import com.mqjd.datamodel.field.object.ObjectField;
import com.mqjd.datamodel.schema.Schema;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.SimpleCompiler;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private static void analyzePojoConfig(PojoConfig config) {
        Schema schema = config.getSchema();

        schema.getProperties()
                .forEach(
                        (k, v) -> {
                            BasicType type = v.getType();
                            Field field = Field.newBuilder().name(k).type(type.name()).build();
                        });
    }

    private static boolean isPojoType(BasicField basicField) {
        if (basicField instanceof ObjectField) {
            ObjectField objectField = (ObjectField) basicField;
            boolean notEmptyProperties = ObjectUtils.isEmpty(objectField.getProperties());
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
