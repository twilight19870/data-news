package ${Curr.package};<#include "include/setId.ftl" />

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import ${Config['basePackage']}.base.BaseEntity.BaseView;
import ${Config['basePackage']}.base.Filter;
import ${Config['basePackage']}.base.Filter.Operator;
import ${Config['basePackage']}.base.FilterForm;
import ${Config['basePackage']}.base.Page;
import ${Config['basePackage']}.base.Pageable;
import ${Config['basePackage']}.auth.CurrentUser;
import  ${Config['basePackage']}.utils.ResultUtils;

import ${Config['basePackage']}.auth.JwtAccess;
import ${Data["package.entity"]}.${Data["entity.java"]};
import ${Data["package.iservice"]}.${Data["iservice.java"]};
<#include "include/swagger_packages_api.ftl"/>
/**
 * Controller - <#if table.tableComment??>${table.tableComment}</#if>
 * 
 * @author ${Config.author}
 * @version ${Config.version}
 * @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
@RestController("api${Data["apiController.java"]}")
@RequestMapping("/api/${Data["entity.java"]?uncap_first}")<#assign entityName=(Data["entity.java"])?uncap_first />
public class ${Data["apiController.java"]} {

	@Inject<#assign serviceName=(Data["iservice.java"])?uncap_first />
	private ${Data["iservice.java"]} ${serviceName};
	
	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@JsonView(BaseView.class)
	public ${Data["entity.java"]} detail(${idClass} id, @CurrentUser JwtAccess currUser) {
		if(currUser==null) {
			ResultUtils.throwUnauthorized();
		}
		${Data["entity.java"]} ${entityName} = ${serviceName}.find(id);
		return ${entityName};
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@JsonView(BaseView.class)
	public List<${Data["entity.java"]}> list(FilterForm filterForm, @CurrentUser JwtAccess currUser) {
		if(filterForm==null) {
			filterForm = new FilterForm();
		}
		if(filterForm.getFilters()==null) {
			filterForm.setFilters(new ArrayList(2));
		}
		//filterForm.getFilters().add(new Filter("memberId", Operator.EQ, currUser.getId()));
		
		List<${Data["entity.java"]}> data = ${serviceName}.findList(1000, filterForm.getFilters(), filterForm.getOrders());
		return data;
	}

	/**
	 * 分页查询
	 */
	@GetMapping("/page")
	@JsonView(BaseView.class)
	public Page page(Pageable pageable, @CurrentUser JwtAccess currUser) {
		List<Filter> filters = pageable.getFilters();
		if(filters==null) {
			filters = new ArrayList();
			pageable.setFilters(filters);
		}
		//filters.add(new Filter("memberId", Operator.EQ, currUser.getId()));
		
		Page page = ${serviceName}.findPage(pageable);
		return page;
	}
	<#if Config["apiEdit"] == "open">

	/**
	 * 保存
	 */
	@PostMapping("/save")
	@JsonView(BaseView.class)
	public ${idClass} save(${Data["entity.java"]} ${entityName}, @CurrentUser JwtAccess currUser) {
		if(currUser==null) {
			ResultUtils.throwUnauthorized();
		}
		if(${entityName}==null) {
			ResultUtils.throwUnprocessableEntity();
		}
		${serviceName}.save(${entityName});
		return ${entityName}.getId();
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	@JsonView(BaseView.class)
	public ${idClass} update(${idClass} id, ${Data["entity.java"]} ${entityName}, @CurrentUser JwtAccess currUser) {
		if(id==null) {
			ResultUtils.throwBadRequest();
		}
		if(${entityName}==null) {
			ResultUtils.throwUnprocessableEntity();
		}
		if(currUser==null) {
			ResultUtils.throwUnauthorized();
		}
		${serviceName}.update(${entityName});
		return id;
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@JsonView(BaseView.class)
	public Long[] remove(Long[] ids, @CurrentUser JwtAccess currUser) {
		for(Long id : ids) {
			${Data["entity.java"]} pojo = ${serviceName}.find(id);
			if(pojo==null) {
				ResultUtils.throwForbidden();
			}
		}
		
		${serviceName}.delete(ids);
		return ids;
	}
	</#if>
}