package com.xt.data.news.plugin.news;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.xt.data.news.base.FilterUtil;
import com.xt.data.news.dto.NewsDto;
import com.xt.data.news.entity.NewsPluginSetting;
import com.xt.data.news.event.ChannelEventDto;
import com.xt.data.news.plugin.NewsPlugin;
import com.xt.data.news.plugin.PluginProperty;
import com.xt.data.news.service.NewsPluginSettingService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * 基础新闻插件
 * @author vivi207
 *
 */
@Slf4j
@Data
public abstract class BaseNewsPlugin<T extends NewsDto> implements NewsPlugin<T> {
	
	protected Set<String> channelPluginIds;
	
	private Map<String, String> attributes;
	
	private List<PluginProperty> pluginPropertys;
	
	@Value("${xt.data.news.news.plugin.thread-pool-size:20}")
	private int threadPoolSize = 20;
	
	private ExecutorService executorService;
	
	@Autowired
	private NewsPluginSettingService newsPluginSettingService;

	@Override
	public int orders() {
		return 1;
	}
	
	@Override
	public String getVersion() {
		return "0.0.1";
	}
	
	public String getAttribute(String name) {
		if(name==null || getAttributes()==null || getAttributes().isEmpty()) {
			return null;
		}
		return getAttributes().get(name);
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * 插件属性注解集合
	 * @return
	 * @throws IntrospectionException
	 */
	public List<PluginProperty> pluginPropertys() throws IntrospectionException {
		if(pluginPropertys!=null) {
			return pluginPropertys;
		}
		
		pluginPropertys = new ArrayList();
		for(Method method : this.getClass().getDeclaredMethods()) {
			PluginProperty prop = method.getAnnotation(PluginProperty.class);
			if(prop==null) {
				continue;
			}
			pluginPropertys.add(prop);
		}
        return pluginPropertys;
	}
	
	@Override
	public boolean enabled() {
		String attr = getAttribute("enabled");
		return "true".equals(attr);
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void reset() {
		setChannelPluginIds(null);
		setAttributes(null);
	}

	public ExecutorService getExecutorService() {
		if(executorService==null) {
			executorService = Executors.newFixedThreadPool(threadPoolSize);
		}
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	@Override
	public ChannelEventDto trigger(List data) {
		Set<String> channelPluginIds = getChannelPluginIds();
		if(channelPluginIds==null || channelPluginIds.isEmpty()) {
			log.debug("channelPluginIds enpty. untrigger.");
			return null;
		}
		ChannelEventDto dto = new ChannelEventDto<>();
		dto.setData(data);
		dto.setChannelPluginIds(channelPluginIds.toArray(new String[] {}));
		return dto;
	}
	
	@Override
	public Set<String> getChannelPluginIds() {
		if(this.channelPluginIds==null) {
			List<NewsPluginSetting> newsPluginSettings = newsPluginSettingService.findList(2000, FilterUtil.create().eq("newsPluginId", getId()).eq("newsPluginId", getId()).filters(), null);
			Set<String> channelPluginIds =  newsPluginSettings.stream().map(i->i.getChannelPluginId()).collect(Collectors.toSet());
			log.info("find database channelPluginIds:{}", channelPluginIds);
			setChannelPluginIds(channelPluginIds);
		}
		return this.channelPluginIds;
	}
}
