<#list table.columns as col>
<#if col.columnName=='orders'>
<#assign hasOrders=true />
</#if>
</#list>
<#if !(hasOrders??)>
<#assign hasOrders=false />
</#if>