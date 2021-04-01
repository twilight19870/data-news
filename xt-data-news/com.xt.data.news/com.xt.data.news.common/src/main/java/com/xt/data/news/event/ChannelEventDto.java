package com.xt.data.news.event;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

/**
 * 频道对象
 * @author vivi207
 *
 */
@Data
public class ChannelEventDto<T> {
	/** 频道插件id **/
	protected String[] channelPluginIds;
	
	/** 领域名称 **/
	protected List<T> data;
	
	@Override
	public String toString() {
		return "ChannelEventDto [channelPluginIds=" + Arrays.toString(channelPluginIds) + ", data=" + data + "]";
	}
}
