package ${packageName};

import com.fasterxml.jackson.annotation.JsonProperty;
<#list imports as import>
import ${import};
</#list>

public class ${className} {

    <#list fields as field>
    @JsonProperty("${field.name}")
    private ${field.type} ${field.fieldName};
    </#list>

}