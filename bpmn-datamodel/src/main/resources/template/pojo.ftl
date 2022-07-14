package ${pojo.packageName};

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mqjd.validator.SchemaValidator;
import com.mqjd.datamodel.utils.JsonUtils;
import java.util.List;
import java.util.HashSet;
import java.util.Objects;
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
    public ${fieldItem.type} ${fieldItem.fieldName};

    </#list>

    <#list pojo.fields as fieldItem>
    @JsonIgnore
    private boolean is${fieldItem.fieldName}Valid() {
        if (${fieldItem.fieldName} != null) {
            return <@valid field=fieldItem/>;
        }
        return true;
    }

    </#list>

    @JsonIgnore
    private boolean isRequiredValid() {
        return <@pojoRequiredValid field=pojo/>;
    }

    @JsonIgnore
    public boolean isValid() {
        return isRequiredValid() && <@fieldsValid fields=pojo.fields/>;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        ${pojo.className} that = (${pojo.className}) o;
        return <@equals fields=pojo.fields/>;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), <@hashCode fields=pojo.fields/>);
    }
}

<#macro fieldsValid fields>
    <#compress>
        <#list fields as itemField>
             <#t>is${itemField.fieldName}Valid()<#sep> && </#sep>
        </#list>
    </#compress>
</#macro>

<#macro equals fields>
    <#compress>
        <#list fields as fieldItem>
             <#t>Objects.equals(${fieldItem.fieldName}, that.${fieldItem.fieldName})<#sep> && </#sep>
        </#list>
    </#compress>
</#macro>

<#macro hashCode fields>
    <#compress>
        <#list fields as fieldItem>
             <#t>${fieldItem.fieldName}<#sep>, </#sep>
        </#list>
    </#compress>
</#macro>

<#macro valid field>
    <#compress>
        true  <#t>
        <#switch field.schema.type>
          <#case "OBJECT">
             <@validObject field=field/>
             <#break>
          <#case "ARRAY">
             <@validArray field=field/>
             <#break>
          <#case "STRING">
             <@validString field=field/>
             <#break>
          <#case "INTEGER">
             <@validInteger field=field/>
             <#break>
          <#case "NUMBER">
             <@validNumber field=field/>
             <#break>
          <#default>
            && true
        </#switch>
    </#compress>
</#macro>

<#macro validObject field>
    && ${field.fieldName}.isValid()
</#macro>

<#macro validArray field>
    <#compress>
        <#if field.schema.minItems??>
            && ${field.fieldName}.size() >= ${field.schema.minItems} <#t>
        </#if>
        <#if field.schema.maxItems??>
            && ${field.fieldName}.size() <= ${field.schema.maxItems} <#t>
        </#if>
        <#if field.schema.uniqueItems??>
            <#if field.schema.uniqueItems>
                && ${field.fieldName}.size() == new HashSet<>(${field.fieldName}).size()
            </#if>
        </#if>
    </#compress>
</#macro>

<#macro validString field>
    <#compress>
        <#if field.schema.minLength??>
            && ${field.fieldName}.length() >= ${field.schema.minLength} <#t>
        </#if>
        <#if field.schema.maxLength??>
            && ${field.fieldName}.length() <= ${field.schema.maxLength} <#t>
        </#if>
        <#if field.schema.enums??>
            && ${field.fieldName}$enums.contains(${field.fieldName}) <#t>
        </#if>
        <#if field.schema.pattern??>
            && Pattern.matches(${codeGenerate(field.schema.pattern)}, ${field.fieldName})
        </#if>
    </#compress>
</#macro>

<#macro validInteger field>
    <#compress>
        <#if field.schema.minimum??>
            && ${field.fieldName} >= ${field.schema.minimum}L <#t>
        </#if>
        <#if field.schema.maximum??>
            && ${field.fieldName} <= ${field.schema.maximum}L <#t>
        </#if>
        <#if field.schema.exclusiveMinimum??>
            && ${field.fieldName} > ${field.schema.exclusiveMinimum}L <#t>
        </#if>
        <#if field.schema.exclusiveMaximum??>
            && ${field.fieldName} <= ${field.schema.exclusiveMaximum}L <#t>
        </#if>
        <#if field.schema.multipleOf??>
            && ${field.fieldName} % ${field.schema.multipleOf}L == 0 <#t>
        </#if>
        <#if field.schema.enums??>
            && ${field.fieldName}$enums.contains(${field.fieldName})
        </#if>
    </#compress>
</#macro>

<#macro validNumber field>
    <#compress>
        <#if field.schema.minimum??>
            && ${field.fieldName} >= ${field.schema.minimum}D <#t>
        </#if>
        <#if field.schema.maximum??>
            && ${field.fieldName} <= ${field.schema.maximum}D <#t>
        </#if>
        <#if field.schema.exclusiveMinimum??>
            && ${field.fieldName} > ${field.schema.exclusiveMinimum}D <#t>
        </#if>
        <#if field.schema.exclusiveMaximum??>
            && ${field.fieldName} <= ${field.schema.exclusiveMaximum}D <#t>
        </#if>
        <#if field.schema.multipleOf??>
            && ${field.fieldName} % ${field.schema.multipleOf}D == 0 <#t>
        </#if>
        <#if field.schema.enums??>
            && ${field.fieldName}$enums.contains(${field.fieldName})
        </#if>
    </#compress>
</#macro>

<#macro pojoRequiredValid field>
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