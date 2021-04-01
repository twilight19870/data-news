<#if (Config["swagger"])?? && Config["swagger"]=="open">
@ApiModel(description="${(table.tableComment)!""}")
</#if>