package ${Curr.package};<#include "include/setId.ftl" />

import java.util.Map;

import com.isoftstone.ctrs.common.sql.util.SQL;

/**
 * <#if table.tableComment??>${table.tableComment}</#if>query condition
 * @author ${Config.author}
 * @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */<#include "include/setId.ftl">
public class ${Data["entity.java"]}SqlQueryCondition{
	
	public static void build(StringBuffer sql, Map<String,Object> qryParams, Map params){
		if(qryParams==null || qryParams.size()==0){
			return;
		}<#assign tv="t1.">
		<#include "include/addSqlQueryCondition.ftl" />
	}
}
