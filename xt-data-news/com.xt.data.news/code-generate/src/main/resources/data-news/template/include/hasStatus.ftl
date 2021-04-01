<#list table.columns as col>
<#if col.columnName=='status'>
<#assign hasStatus=true />
<#assign statusClass=Util.string.last(Util.jdbc.toJdbcClassByType(col.dataType),"\\.") />
<#assign statusName=Util.string.toFiledName(col.columnName) />
<#assign statusColumn=col.columnName />
<#assign statusComment=col.columnComment />
<#assign statusList=Util.string.kvList(col.columnComment) />
</#if>
</#list>
<#if !hasStatus??>
<#assign hasStatus=false />
</#if>