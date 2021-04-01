package com.xt.data.news.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import com.xt.data.news.base.Page;
import com.xt.data.news.base.Pageable;

import com.xt.data.news.entity.NewsPluginSetting;
import com.xt.data.news.service.NewsPluginSettingService;

/**
 * ServiceImpl - 新闻频道插件设置
 * 
 * @author vivi207
 * @version xt V0.0.1
 * @date 2021-03-31 10:10:20
 */
@Service
public class NewsPluginSettingServiceImpl extends BaseServiceImpl<NewsPluginSetting, Long> implements NewsPluginSettingService {

	@Override
	@Transactional
	public NewsPluginSetting save(NewsPluginSetting newsPluginSetting) {
		return super.save(newsPluginSetting);
	}

	@Override
	@Transactional
	public NewsPluginSetting update(NewsPluginSetting newsPluginSetting) {
		return super.update(newsPluginSetting);
	}

	@Override
	@Transactional
	public NewsPluginSetting update(NewsPluginSetting newsPluginSetting, String... ignoreProperties) {
		return super.update(newsPluginSetting, ignoreProperties);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	public void delete(NewsPluginSetting newsPluginSetting) {
		super.delete(newsPluginSetting);
	}

}