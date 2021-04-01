package ${Curr.package};<#include "include/setId.ftl" />

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import ${Config['basePackage']}.base.Page;
import ${Config['basePackage']}.base.Pageable;

import ${Data["package.entity"]}.${Data["entity.java"]};
import ${Data["package.iservice"]}.${Data["iservice.java"]};

/**
 * ServiceImpl - ${tableComment}
 * 
 * @author ${Config.author}
 * @version ${Config.version}
 * @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
@Service
public class ${Data["service.java"]} extends BaseServiceImpl<${Data["entity.java"]}, ${idClass}> implements ${Data["iservice.java"]} {

	@Override<#assign entityName=(Data["entity.java"])?uncap_first />
	@Transactional
	public ${Data["entity.java"]} save(${Data["entity.java"]} ${entityName}) {
		return super.save(${entityName});
	}

	@Override
	@Transactional
	public ${Data["entity.java"]} update(${Data["entity.java"]} ${entityName}) {
		return super.update(${entityName});
	}

	@Override
	@Transactional
	public ${Data["entity.java"]} update(${Data["entity.java"]} ${entityName}, String... ignoreProperties) {
		return super.update(${entityName}, ignoreProperties);
	}

	@Override
	@Transactional
	public void delete(${idClass} id) {
		super.delete(id);
	}

	@Override
	@Transactional
	public void delete(${idClass}... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	public void delete(${Data["entity.java"]} ${entityName}) {
		super.delete(${entityName});
	}

}