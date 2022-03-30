package ${packageName};

<#list imports as import>
import ${import};
</#list>

public class ${className} {

    <#list schema.fields as field>
    private ${field.type} ${field.title};
    </#list>

}