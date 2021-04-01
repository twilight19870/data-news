<#if (Config.jsonView)?? &&Config.jsonView=='open'>
<#if col.dataType!='TEXT' && col.dataType!='CLOB'>
	@JsonView(BaseView.class)
</#if>
</#if>