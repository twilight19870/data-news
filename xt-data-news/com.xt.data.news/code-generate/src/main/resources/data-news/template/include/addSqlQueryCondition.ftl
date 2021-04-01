<#if (Config.queryCondition)?? && Config.queryCondition!=''>
<#list table.columns as col><#assign colName=Util.string.toFiledName(col.columnName, 0, true)><#assign colJavaType=Util.string.last(Util.jdbc.toJdbcClassByType(col.dataType),"\\.")>
	<#if colJavaType=='Date' || colJavaType=='Timestamp'>
		SQL.append(sql, params, " and ${(tv)!}${col.columnName} = date_format(:${colName}, '%Y-%m-%d') ", qryParams, "${colName}");
	<#else>	
		SQL.append(sql, params, " and ${(tv)!}${col.columnName} = :${colName} ", qryParams, "${colName}");
	</#if>
<#if Config.queryCondition=='all'>
	<#if col.columnKey='PRI'>
		SQL.in(sql, params, " and ${(tv)!}${col.columnName} in ", qryParams, "${colName}In");
	</#if>	
	<#if colJavaType=='String'>	
		<#if (col.characterMaximumLength)?? && (col.characterMaximumLength>32)>
		SQL.append(sql, params, " and ${(tv)!}${col.columnName} like concat('%', :${colName}Like , '%') ", qryParams, "${colName}Like");
		<#else>
		</#if>
	<#elseif colJavaType='Long' || colJavaType='Integer' || colJavaType='Double'>
		SQL.append(sql, params, " and ${(tv)!}${col.columnName} >= :${colName}Ge ", qryParams, "${colName}Ge");
		SQL.append(sql, params, " and ${(tv)!}${col.columnName} <= :${colName}Le ", qryParams, "${colName}Le");
	<#elseif colJavaType=='Date' || colJavaType=='Timestamp'>
		SQL.append(sql, params, " and ${(tv)!}${col.columnName} >= date_format(:${colName}Begin, '%Y-%m-%d') ", qryParams, "${colName}Begin");
		SQL.append(sql, params, " and ${(tv)!}${col.columnName} < date_add(date_format(:${colName}End, '%Y-%m-%d'), interval 1 day) ", qryParams, "${colName}End");
	</#if>
</#if>
</#list>
</#if>