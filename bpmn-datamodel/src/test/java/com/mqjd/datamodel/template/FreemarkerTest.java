package com.mqjd.datamodel.template;

import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.StringWriter;

public class FreemarkerTest {

    @Test
    public void testFreemarker() throws Exception {
        String className = "XRunner";
        String packageName = "com.mqjd.datamodel.model";
        Configuration cfg = new Configuration(Configuration.getVersion());
        cfg.setClassForTemplateLoading(this.getClass(), "/freemarker");
        Template template = cfg.getTemplate("java_bean.ftl");
        StringWriter stringWriter = new StringWriter();
        template.process(
                ImmutableMap.of("className", className, "packageName", packageName), stringWriter);
        System.out.println(stringWriter);
    }
}
