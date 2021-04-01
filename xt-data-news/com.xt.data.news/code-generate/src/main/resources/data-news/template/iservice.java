package ${Curr.package};<#include "include/setId.ftl" />

import ${Config['basePackage']}.base.BaseService;
import ${Data["package.entity"]}.${Data["entity.java"]};

/**
 * Service - ${tableComment}
 * 
 * @author ${Config.author}
 * @version ${Config.version}
 * @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
public interface ${Data["iservice.java"]} extends BaseService<${Data["entity.java"]}, ${idClass}>{

}