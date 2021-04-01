package ${Curr.package};<#include "include/setId.ftl" />

import ${Config['basePackage']}.base.BaseDao;
import ${Data["package.entity"]}.${Data["entity.java"]};

/**
 * <#if table.tableComment??>${table.tableComment}</#if>repository接口
 * @author ${Config.author}
 * @version ${Config.version}
 * @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
public interface ${Data["dao.java"]} extends BaseDao<${Data["entity.java"]}, ${idClass}> {
	
	
}