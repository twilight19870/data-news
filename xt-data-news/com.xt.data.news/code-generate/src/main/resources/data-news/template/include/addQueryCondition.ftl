<#if (Config.queryCondition)?? && Config.queryCondition!=''>
<#list table.columns as col><#assign colName=Util.string.toFiledName(col.columnName)><#assign colJavaType=Util.string.last(Util.jdbc.toJdbcClassByType(col.dataType),"\\.")>
				Object ${colName} = qryParams.get("${colName}");
				if(${colName}!=null && !"".equals(${colName})){
				<#if colJavaType=='Date' || colJavaType=='Timestamp'>
					list.add(cb.equal(root.<${colJavaType}>get("${colName}"), (${colJavaType})DateUtil.to(${colName}.toString(), ${colJavaType}.class)));
				<#else>	
					list.add(cb.equal(root.<${colJavaType}>get("${colName}"), ${colName}));
				</#if>	
				}
		<#if Config.queryCondition=='all'>
			<#if col.columnKey='PRI'>
				Object ${colName}In = qryParams.get("${colName}In");
				if(${colName}In!=null && !"".equals(${colName}In)){
					list.add(cb.in(root.<${colJavaType}>get("${colName}").in(${colName}In.toString().split(","))));
				}
			</#if>	
			<#if colJavaType=='String'>	
				<#if (col.characterMaximumLength)?? && (col.characterMaximumLength>32)>
				Object ${colName}Like = qryParams.get("${colName}Like");
				if(${colName}Like!=null && !"".equals(${colName}Like)){
					list.add(cb.like(root.<${colJavaType}>get("${colName}"), "%"+${colName}Like+"%"));
				}
				<#else>
				</#if>
			<#elseif colJavaType='Long' || colJavaType='Integer' || colJavaType='Double'>
				Object ${colName}Ge = qryParams.get("${colName}Ge");
				if(${colName}Ge!=null && !"".equals(${colName}Ge)){
					list.add(cb.ge(root.<${colJavaType}>get("${colName}"), ${colJavaType}.valueOf(${colName}Ge.toString())));
				}
				Object ${colName}Le = qryParams.get("${colName}Le");
				if(${colName}Le!=null && !"".equals(${colName}Le)){
					list.add(cb.le(root.<${colJavaType}>get("${colName}"), ${colJavaType}.valueOf(${colName}Le.toString())));
				}
			<#elseif colJavaType=='Date' || colJavaType=='Timestamp'>
				Object ${colName}Begin = qryParams.get("${colName}Begin");
				if(${colName}Begin!=null && !"".equals(${colName}Begin)){
					list.add(cb.greaterThanOrEqualTo(root.<${colJavaType}>get("${colName}"), (${colJavaType})DateUtil.to(${colName}Begin.toString(), ${colJavaType}.class)));
				}
				Object ${colName}End = qryParams.get("${colName}End");
				if(${colName}End!=null && !"".equals(${colName}End)){
					list.add(cb.lessThanOrEqualTo(root.<${colJavaType}>get("${colName}"), (${colJavaType})DateUtil.to(${colName}End.toString(), ${colJavaType}.class)));
				}
			</#if>
		</#if>
</#list>
</#if>