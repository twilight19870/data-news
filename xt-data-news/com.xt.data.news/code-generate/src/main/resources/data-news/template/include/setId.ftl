<#list table.columns as col>
<#if (col.columnKey)?? && col.columnKey='PRI'>
<#assign idClass=Util.string.last(Util.jdbc.toJdbcClassByType(col.dataType),"\\.") />
<#assign idName=Util.string.toFiledName(col.columnName) />
<#assign idColumn=col.columnName />
</#if>
</#list>
<#assign tableComment=Util.string.replaceEnd(Util.string.splitFirst(table.tableComment, ",|，|;|；|、| "),"表","") />