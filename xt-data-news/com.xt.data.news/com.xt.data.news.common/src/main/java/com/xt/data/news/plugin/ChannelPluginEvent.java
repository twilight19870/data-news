package com.xt.data.news.plugin;

import org.springframework.context.ApplicationEvent;

import lombok.Data;

/**
 * 平道插件 Event
 * @author vivi207
 *
 */
@Data
public class ChannelPluginEvent<ChannelPlugin, R> extends ApplicationEvent {

	private static final long serialVersionUID = 2477915639507703071L;

	private ChannelPluginMonitorPoint channelPluginMonitorPoint;
	
	private ChannelPluginMonitorAction channelPluginMonitorAction;
	
	private R results;
	
	private Exception exception;

	public ChannelPluginEvent(ChannelPlugin source, ChannelPluginMonitorPoint point, ChannelPluginMonitorAction action) {
		super(source);
		this.channelPluginMonitorPoint = point;
		this.channelPluginMonitorAction = action;
	}
	
	public ChannelPluginEvent(ChannelPlugin source, ChannelPluginMonitorPoint point, ChannelPluginMonitorAction action, Exception exception) {
		super(source);
		this.channelPluginMonitorPoint = point;
		this.channelPluginMonitorAction = action;
		this.exception = exception;
	}
	
	public ChannelPluginEvent(ChannelPlugin source, ChannelPluginMonitorPoint point, ChannelPluginMonitorAction action, R results) {
		super(source);
		this.channelPluginMonitorPoint = point;
		this.channelPluginMonitorAction = action;
		this.exception = exception;
		this.results = results;
	}

	public ChannelPlugin getChannelPlugin() {
		return (ChannelPlugin)getSource();
	}
}
