package ${packageName};

<#list imports as import>
import ${import};
</#list>

public class ${className} {

    <#list fields as field>
    private ${field.type} ${field.name};
    </#list>

}