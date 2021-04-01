package com.xt.message.center.app.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.xt.data.news.base.FilterUtil;
import com.xt.data.news.entity.CustChannelPluginSetting;
import com.xt.data.news.plugin.ChannelPlugin;
import com.xt.data.news.plugin.ChannelPluginEvent;
import com.xt.data.news.plugin.ChannelPluginMonitor;
import com.xt.data.news.plugin.ChannelPluginMonitorAction;
import com.xt.data.news.plugin.ChannelPluginMonitorPoint;
import com.xt.data.news.service.CustChannelPluginSettingService;
import com.xt.data.news.utils.ResultUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 频道工厂
 * @author vivi207
 *
 */
@Slf4j
@Configuration
public class ChannelPluginFactory implements ApplicationListener<ChannelPluginEvent>{
	/** 监控点 **/
	private ThreadLocal<Map<ChannelPluginMonitorPoint, ChannelPluginMonitor>> monitors = new ThreadLocal<>();
	
	/** 频道缓存 **/
	private Map<String, ChannelPlugin> channelPlugins = new ConcurrentHashMap<>();
	
	@Autowired(required = false)
	private List<ChannelPlugin> channelPluginList;
	
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private CustChannelPluginSettingService custChannelPluginSettingService;
	
	@PostConstruct
	public void init() {
		log.info("init news plugins");
		if(channelPluginList==null) {
			log.info("news plugins is empty");
			return;
		}
		channelPluginList.forEach(i -> {
			log.info("init add news plugin, id:{}, name:{}, domain:{}, timer:{}, class:{}", i.getId(), i.getName(), i.getDomain(), i.getTimer(), i.getClass());
			channelPlugins.put(i.getId(), i);
		});
	}
	
	/**
	 * 获取频道插件
	 * @param channelPluginId
	 * @return
	 */
	public ChannelPlugin get(String channelPluginId) {
		return channelPlugins.get(channelPluginId);
	}
	
	/**
	 * 获取频道插件列表
	 * @return
	 */
	public Collection<ChannelPlugin> getChannelPlugins() {
		return channelPlugins.values();
	}
	

	/**
	 * 执行频道处理
	 * @param news
	 */
	public void execute(String channelPluginId, List data) {
		log.debug("channelPluginId:{}, data:{}", channelPluginId, data);
		if(channelPluginId==null) {
			ResultUtils.throwFail("频道插件ID不能為空");
		}
		ChannelPlugin channelPlugin = get(channelPluginId);
		this.execute(channelPlugin, data);
	}
	
	/**
	 * 执行频道处理
	 * @param news
	 */
	public void execute(ChannelPlugin channelPlugin, List data) {
		log.debug("channelPlugin:{}, data:{}", channelPlugin, data);
		if(channelPlugin==null) {
			log.error("channelPlugin {} not found, bypassed!", channelPlugin);
			ResultUtils.throwFail("频道插件不存在");
		}
		
		boolean isPublish = false;
		applicationContext.publishEvent(new ChannelPluginEvent(channelPlugin, ChannelPluginMonitorPoint.TRIGGER, ChannelPluginMonitorAction.BEFORE));
		try {
			isPublish = channelPlugin.trigger(data);
		} catch (Exception e) {
			applicationContext.publishEvent(new ChannelPluginEvent(channelPlugin, ChannelPluginMonitorPoint.TRIGGER, ChannelPluginMonitorAction.ERROR, e));
			throw e;
		}
		applicationContext.publishEvent(new ChannelPluginEvent(channelPlugin, ChannelPluginMonitorPoint.TRIGGER, ChannelPluginMonitorAction.AFTER, isPublish));
		
		if(isPublish) {
			applicationContext.publishEvent(new ChannelPluginEvent(channelPlugin, ChannelPluginMonitorPoint.CUSTOMS, ChannelPluginMonitorAction.BEFORE));
			List<CustChannelPluginSetting> custChannelPluginSettings = custChannelPluginSettingService.findList(10000, FilterUtil.create().eq("channelPluginId", channelPlugin.getId()).filters(), null);
			log.info("channelPluginId:{}, find subscribe customs:{}", channelPlugin.getId(), custChannelPluginSettings.size());
			applicationContext.publishEvent(new ChannelPluginEvent(channelPlugin, ChannelPluginMonitorPoint.CUSTOMS, ChannelPluginMonitorAction.AFTER, custChannelPluginSettings));
			if(custChannelPluginSettings.isEmpty()) {
				return ;
			}
			
			for(CustChannelPluginSetting item : custChannelPluginSettings) {
				try {
					log.debug("channelPluginId {} publish, customId:{}, attrs:{}", channelPlugin.getId(), item.getUsername(), item.getAttributes());
					applicationContext.publishEvent(new ChannelPluginEvent(channelPlugin, ChannelPluginMonitorPoint.PUBLISH, ChannelPluginMonitorAction.BEFORE));
					Map customParams = item.getAttributes()==null?null:JSON.parseObject(item.getAttributes(), HashMap.class);
					String msgId = channelPlugin.publish(item.getUsername(), customParams);
					applicationContext.publishEvent(new ChannelPluginEvent(channelPlugin, ChannelPluginMonitorPoint.PUBLISH, ChannelPluginMonitorAction.AFTER, msgId));
				} catch (Exception e) {
					log.error("channelPluginId:{}, customId:{}, attrs:{}", channelPlugin.getId(), item.getUsername(), item.getAttributes(), e);
					applicationContext.publishEvent(new ChannelPluginEvent(channelPlugin, ChannelPluginMonitorPoint.PUBLISH, ChannelPluginMonitorAction.ERROR, e));
				}
			}
		}else {
			log.info("not publish, channelPluginId:{}", channelPlugin.getId());
		}
	}
	
	/**
	 * 注册监控点
	 * @param point
	 * @param monitor
	 */
	public void registerMonitor(ChannelPluginMonitorPoint point, ChannelPluginMonitor monitor) {
		if(point==null || monitor==null) {
			throw new RuntimeException("参数不能为空");
		}
		Map monitorList = monitors.get();
		if(monitorList==null) {
			monitorList = new HashMap();
			monitors.set(monitorList);
		}
		monitorList.put(point, monitor);
	}
	
	/**
	 * 注册监控点
	 * @param point
	 * @param monitor
	 */
	public void removeMonitor(ChannelPluginMonitorPoint point) {
		if(point==null) {
			return;
		}
		Map monitorList = monitors.get();
		if(monitorList!=null) {
			monitorList.remove(point);
		}
	}
	
	/**
	 * 清空注册点
	 * @param point
	 * @param monitor
	 */
	public void clearMonitor() {
		Map monitorList = monitors.get();
		if(monitorList!=null) {
			monitorList.clear();
			monitors.set(null);
		}
	}

	/**
	 * 事件统一监听
	 */
	@Override
	public void onApplicationEvent(ChannelPluginEvent event) {
		//获取线程缓存
		ChannelPluginMonitorPoint point = event.getChannelPluginMonitorPoint();
		Map<ChannelPluginMonitorPoint, ChannelPluginMonitor> monitorList = monitors.get();
		if(monitorList==null || monitorList.isEmpty()) {
			log.debug("monitor empty");
			return;
		}
		
		//获取监控点
		ChannelPluginMonitor channelPluginMonitor = monitorList.get(point);
		if(channelPluginMonitor==null) {
			log.debug("unregister monitor:{}", point);
			return;
		}

		//执行动作
		ChannelPluginMonitorAction action = event.getChannelPluginMonitorAction();
		switch (action) {
			case BEFORE: channelPluginMonitor.before((ChannelPlugin)event.getChannelPlugin()); break;
			case AFTER: channelPluginMonitor.after((ChannelPlugin)event.getChannelPlugin(), event.getResults()); break;
			case ERROR: channelPluginMonitor.error((ChannelPlugin)event.getChannelPlugin(), event.getException()); break;
		}
	}
}
