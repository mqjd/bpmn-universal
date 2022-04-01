package com.mqjd.datamodel.model;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.codehaus.commons.compiler.util.resource.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class PojoResource implements Resource {
    private static final Logger LOG = LoggerFactory.getLogger(PojoResource.class);
    private final Template template;
    private final Pojo pojo;

    public PojoResource(Template template, Pojo pojo) {
        this.template = template;
        this.pojo = pojo;
    }

    @Override
    public InputStream open() throws IOException {
        try {
            StringWriter stringWriter = new StringWriter();
            template.process(pojo, stringWriter);
            String code = stringWriter.toString();
            return new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        } catch (TemplateException e) {
            LOG.error("process template failed: {}", pojo);
            throw new IllegalArgumentException("process template failed", e);
        }
    }

    @Override
    public String getFileName() {
        return pojo.getPackageName().replace(".", "/") + "/" + pojo.getClassName() + ".java";
    }

    @Override
    public long lastModified() {
        return 0;
    }
}
