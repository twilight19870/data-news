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

import com.xt.data.news.entity.ChannelPluginSetting;
import com.xt.data.news.service.ChannelPluginSettingService;

/**
 * ServiceImpl - 频道插件设置
 * 
 * @author vivi207
 * @version xt V0.0.1
 * @date 2021-03-31 10:10:20
 */
@Service
public class ChannelPluginSettingServiceImpl extends BaseServiceImpl<ChannelPluginSetting, Long> implements ChannelPluginSettingService {

	@Override
	@Transactional
	public ChannelPluginSetting save(ChannelPluginSetting channelPluginSetting) {
		return super.save(channelPluginSetting);
	}

	@Override
	@Transactional
	public ChannelPluginSetting update(ChannelPluginSetting channelPluginSetting) {
		return super.update(channelPluginSetting);
	}

	@Override
	@Transactional
	public ChannelPluginSetting update(ChannelPluginSetting channelPluginSetting, String... ignoreProperties) {
		return super.update(channelPluginSetting, ignoreProperties);
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
	public void delete(ChannelPluginSetting channelPluginSetting) {
		super.delete(channelPluginSetting);
	}

}