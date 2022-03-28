package com.mqjd.datamodel.compiler;

import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.codehaus.commons.compiler.IScriptEvaluator;
import org.codehaus.janino.ScriptEvaluator;
import org.codehaus.janino.SimpleCompiler;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;
import java.util.Objects;

public class SchemaCompiler {
    @Test
    public void testJanino() throws Exception {
        String content = "System.out.println(\"Hello world\");";
        IScriptEvaluator evaluator = new ScriptEvaluator();
        evaluator.cook(content);
        evaluator.evaluate(null);
    }

    @Test
    public void testJaninoClass() throws Exception {
        String className = "XRunner";
        String packageName = "com.mqjd.datamodel.model";
        Configuration cfg = new Configuration(Configuration.getVersion());
        cfg.setDirectoryForTemplateLoading(
                new File(Objects.requireNonNull(getClass().getResource("/compiler")).toURI()));
        Template template = cfg.getTemplate("myRunner.ftl");
        StringWriter stringWriter = new StringWriter();
        template.process(
                ImmutableMap.of("className", "XRunner", "packageName", packageName), stringWriter);
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.setParentClassLoader(this.getClass().getClassLoader());
        compiler.cook(stringWriter.toString());
        Class<Runner> aClass =
                (Class<Runner>) compiler.getClassLoader().loadClass(packageName + "." + className);
        aClass.newInstance().run();
    }
}
