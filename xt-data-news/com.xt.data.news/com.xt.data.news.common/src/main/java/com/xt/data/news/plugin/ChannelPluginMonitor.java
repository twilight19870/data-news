package com.xt.data.news.plugin;

/**
 * 监控频道处理插件
 * @author Administrator
 *
 */
public interface ChannelPluginMonitor {

	void before(ChannelPlugin channelPlugin);
	
	void after(ChannelPlugin channelPlugin, Object obj);
	
	void error(ChannelPlugin channelPlugin, Exception ex);
}
