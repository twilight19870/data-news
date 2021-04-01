<#if (col.columnKey)?? && col.columnKey='PRI'>
	@Id
	<#if col.extra=='auto_increment'>
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	</#if>
</#if>
<#if col.dataType='DATETIME' || col.dataType='TIMESTAMP'>
	@Temporal(TemporalType.TIMESTAMP)
</#if>
<#if col.dataType='TEXT' || col.dataType='CLOB'>
	@Lob
</#if>
<#if (col.columnName?index_of("_") > -1)>
	@Column(name="${col.columnName}")
</#if>
<#if col.dataType='VARCHAR' || col.dataType='CHAR'>
	@Length(max = ${col.characterMaximumLength?c})
</#if>
<#if col.isNullable=='NO'>
	@NotNull
</#if>