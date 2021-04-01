<#if (Config["swagger"])?? && Config["swagger"]=="open">
	@ApiParam(value="${col.columnComment!""}"<#if col.isNullable=='YES' && col.columnKey!='PRI'>, required=true</#if>)
</#if>