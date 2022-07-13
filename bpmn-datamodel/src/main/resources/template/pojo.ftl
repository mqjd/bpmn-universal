package ${pojo.packageName};

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mqjd.validator.SchemaValidator;
import com.mqjd.datamodel.utils.JsonUtils;
import java.util.List;
import java.util.regex.Pattern;
<#list pojo.imports as import>
import ${import};
</#list>

public class ${pojo.className} implements SchemaValidator {

    <#list pojo.fields as fieldItem>
        <@enumCode field=fieldItem/>
    </#list>

    <#list pojo.fields as fieldItem>
    @JsonProperty("${fieldItem.name}")
    private ${fieldItem.type} ${fieldItem.fieldName};

    </#list>

    <#list pojo.fields as fieldItem>
    @JsonIgnore
    private boolean is${fieldItem.fieldName}Valid() {
        return <@valid field=fieldItem/>;
    }

    </#list>
    @JsonIgnore
    public boolean isSchemaValid() {
        return <@valid field=pojo/>;
    }

    @JsonIgnore
    public boolean isValid() {
        return isSchemaValid() &&<#list pojo.fields as itemField> is${itemField.fieldName}Valid()<#sep>
        &&</#sep></#list>;
    }
}

<#macro valid field>
    <#compress>
        <#switch field.schema.type>
          <#case "OBJECT">
             <@validObject field=field/>
             <#break>
          <#case "STRING">
             <@validString field=field/>
             <#break>
          <#default>
            true
        </#switch>
    </#compress>
</#macro>

<#macro validString field>
    <#compress>
        <#if field.schema.minLength??>
            ${field.fieldName}.length() >= ${field.schema.minLength}<#else> true <#t>
        </#if>
        <#t> && <#t>
        <#if field.schema.maxLength??>
            ${field.fieldName}.length() <= ${field.schema.maxLength}<#else> true <#t>
        </#if>
        <#t> && <#t>
        <#if field.schema.enums??>
            ${field.fieldName}$enums.contains(${field.fieldName})<#else> true <#t>
        </#if>
        <#t> && <#t>
        <#if field.schema.pattern??>
            Pattern.matches(field.schema.pattern, ${field.fieldName})<#else> true <#t>
        </#if>
    </#compress>
</#macro>

<#macro validObject field>
    <#if field.schema.required??>
        <#list field.schema.required as requiredField>
            <#lt>$${requiredField} != null<#sep> &&</#sep> <#else>true
        </#list>
        <#else>
            true
    </#if>
</#macro>

<#macro enumCode field>
    <#if field.schema.enums??>
    private static List<${field.type}> ${field.fieldName}$enums = ${codeGenerate(field.schema.enums,field.type)};
    </#if>
</#macro>