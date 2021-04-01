package com.xt.data.news.plugin.channel;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.xt.data.news.plugin.ChannelPlugin;
import com.xt.data.news.plugin.PluginProperty;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * 基础频道插件
 * @author vivi207
 *
 */
@Slf4j
@Data
public abstract class BaseChannelPlugin implements ChannelPlugin {
	
	private Map<String, String> attributes;
	
	private List<PluginProperty> pluginPropertys;
	
	private int threadPoolSize = 20;
	
	private ExecutorService executorService;

	@Override
	public int orders() {
		return 1;
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
	public String getVersion() {
		return "0.0.1";
	}
}
