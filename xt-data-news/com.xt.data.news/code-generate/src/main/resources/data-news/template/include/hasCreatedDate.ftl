<#list table.columns as col>
<#if col.columnName=='createdDate'>
<#assign hasCreatedDate=true />
<#assign createdDateClass=Util.string.last(Util.jdbc.toJdbcClassByType(col.dataType),"\\.") />
<#assign createdDateName=Util.string.toFiledName(col.columnName) />
<#assign createdDateColumn=col.columnName />
</#if>
</#list>
<#if !hasCreatedDate??>
<#assign hasCreatedDate=false />
</#if>