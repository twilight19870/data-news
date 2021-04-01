package ${Curr.package};<#include "include/setId.ftl" />

import org.springframework.stereotype.Repository;

import ${Config['basePackage']}.dao.BaseDaoImpl;
import ${Data["package.entity"]}.${Data["entity.java"]};
import ${Data["package.dao"]}.${Data["dao.java"]};

/**
 * DaoImpl - ${tableComment}
 * 
 * @author ${Config.author}
 * @version ${Config.version}
 * @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
@Repository
public class ${Data["daoImpl.java"]} extends BaseDaoImpl<${Data["entity.java"]}, ${idClass}> implements ${Data["dao.java"]} {

}