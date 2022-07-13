package com.mqjd.datamodel.freemarker.function;

import com.mqjd.datamodel.utils.JsonUtils;
import freemarker.ext.beans.StringModel;
import freemarker.ext.util.WrapperTemplateModel;
import freemarker.template.DefaultListAdapter;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateMethodModelEx;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class CodeGenerateFunction implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) {
        Object param = arguments.get(0);
        if (param instanceof WrapperTemplateModel) {
            param = ((WrapperTemplateModel) param).getWrappedObject();
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
