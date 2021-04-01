package com.xt.data.news.ctrl;

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
import com.xt.data.news.base.BaseEntity.BaseView;
import com.xt.data.news.base.Filter;
import com.xt.data.news.base.Filter.Operator;
import com.xt.data.news.base.FilterForm;
import com.xt.data.news.base.Page;
import com.xt.data.news.base.Pageable;
import com.xt.data.news.auth.CurrentUser;
import  com.xt.data.news.utils.ResultUtils;

import com.xt.data.news.auth.JwtAccess;
import com.xt.data.news.entity.NewsPluginSetting;
import com.xt.data.news.service.NewsPluginSettingService;
/**
 * Controller - 新闻频道插件设置
 * 
 * @author vivi207
 * @version xt V0.0.1
 * @date 2021-03-31 10:10:20
 */
@RestController("apiNewsPluginSettingController")
@RequestMapping("/api/newsPluginSetting")
public class NewsPluginSettingController {

	@Inject
	private NewsPluginSettingService newsPluginSettingService;
	
	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@JsonView(BaseView.class)
	public NewsPluginSetting detail(Long id, @CurrentUser JwtAccess currUser) {
		if(currUser==null) {
			ResultUtils.throwUnauthorized();
		}
		NewsPluginSetting newsPluginSetting = newsPluginSettingService.find(id);
		return newsPluginSetting;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@JsonView(BaseView.class)
	public List<NewsPluginSetting> list(FilterForm filterForm, @CurrentUser JwtAccess currUser) {
		if(filterForm==null) {
			filterForm = new FilterForm();
		}
		if(filterForm.getFilters()==null) {
			filterForm.setFilters(new ArrayList(2));
		}
		//filterForm.getFilters().add(new Filter("memberId", Operator.EQ, currUser.getId()));
		
		List<NewsPluginSetting> data = newsPluginSettingService.findList(1000, filterForm.getFilters(), filterForm.getOrders());
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
		
		Page page = newsPluginSettingService.findPage(pageable);
		return page;
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	@JsonView(BaseView.class)
	public Long save(NewsPluginSetting newsPluginSetting, @CurrentUser JwtAccess currUser) {
		if(currUser==null) {
			ResultUtils.throwUnauthorized();
		}
		if(newsPluginSetting==null) {
			ResultUtils.throwUnprocessableEntity();
		}
		newsPluginSettingService.save(newsPluginSetting);
		return newsPluginSetting.getId();
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	@JsonView(BaseView.class)
	public Long update(Long id, NewsPluginSetting newsPluginSetting, @CurrentUser JwtAccess currUser) {
		if(id==null) {
			ResultUtils.throwBadRequest();
		}
		if(newsPluginSetting==null) {
			ResultUtils.throwUnprocessableEntity();
		}
		if(currUser==null) {
			ResultUtils.throwUnauthorized();
		}
		newsPluginSettingService.update(newsPluginSetting);
		return id;
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@JsonView(BaseView.class)
	public Long[] remove(Long[] ids, @CurrentUser JwtAccess currUser) {
		for(Long id : ids) {
			NewsPluginSetting pojo = newsPluginSettingService.find(id);
			if(pojo==null) {
				ResultUtils.throwForbidden();
			}
		}
		
		newsPluginSettingService.delete(ids);
		return ids;
	}
}