package com.mqjd.datamodel.freemarker.function;

import com.mqjd.datamodel.utils.JsonUtils;
import freemarker.ext.util.WrapperTemplateModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateScalarModel;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class CodeGenerateFunction implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) {
        Object param = arguments.get(0);
        if (param instanceof WrapperTemplateModel) {
            param = ((WrapperTemplateModel) param).getWrappedObject();
        }
        if (param instanceof TemplateScalarModel) {
            return String.format("\"%s\"", StringEscapeUtils.escapeJava(arguments.get(0).toString()));
        }
        String json = JsonUtils.toJson(param);
        if (param instanceof List) {
            return String.format("JsonUtils.jsonToList(\"%s\", %s.class)", StringEscapeUtils.escapeJava(json),
                                 arguments.get(1).toString()
            );
        }
        return String.format("JsonUtils.jsonToObject(\"%s\", %s.class)", StringEscapeUtils.escapeJava(json),
                             param.getClass().getName()
        );
    }
}
