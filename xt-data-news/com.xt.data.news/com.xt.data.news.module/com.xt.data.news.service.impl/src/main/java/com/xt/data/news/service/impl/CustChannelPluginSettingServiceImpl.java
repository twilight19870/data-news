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

import com.xt.data.news.entity.CustChannelPluginSetting;
import com.xt.data.news.service.CustChannelPluginSettingService;

/**
 * ServiceImpl - 用户频道插件设置
 * 
 * @author vivi207
 * @version xt V0.0.1
 * @date 2021-03-31 10:10:20
 */
@Service
public class CustChannelPluginSettingServiceImpl extends BaseServiceImpl<CustChannelPluginSetting, Long> implements CustChannelPluginSettingService {

	@Override
	@Transactional
	public CustChannelPluginSetting save(CustChannelPluginSetting custChannelPluginSetting) {
		return super.save(custChannelPluginSetting);
	}

	@Override
	@Transactional
	public CustChannelPluginSetting update(CustChannelPluginSetting custChannelPluginSetting) {
		return super.update(custChannelPluginSetting);
	}

	@Override
	@Transactional
	public CustChannelPluginSetting update(CustChannelPluginSetting custChannelPluginSetting, String... ignoreProperties) {
		return super.update(custChannelPluginSetting, ignoreProperties);
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
	public void delete(CustChannelPluginSetting custChannelPluginSetting) {
		super.delete(custChannelPluginSetting);
	}

}