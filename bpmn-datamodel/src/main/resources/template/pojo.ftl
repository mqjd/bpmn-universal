package ${packageName};

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mqjd.validator.SchemaValidator;
<#list imports as import>
import ${import};
</#list>

public class ${className} implements SchemaValidator {

    <#list fields as field>
    @JsonProperty("${field.name}")
    private ${field.type} ${field.fieldName};

    </#list>

    public boolean isValid(){
        return <@valid field=field/>;
    }
}

<#macro valid field>
    <#switch field.type>
      <#case "OBJECT">
         <@validObject field=field/>
         <#break>
      <#case "STRING">
         <@validString field=field/>
         <#break>
      <#default>
        true
    </#switch>
</#macro>

<#macro validString field>
    true
</#macro>

<#macro validObject field>
    <#if field.required??>
        <#list field.required as requiredField>
            $${requiredField} != null
            <#sep>&&</#sep>
            <#else>
                true
        </#list>
        <#else>
            true
    </#if>
</#macro>