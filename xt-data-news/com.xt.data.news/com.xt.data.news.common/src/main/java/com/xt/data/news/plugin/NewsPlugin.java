package com.xt.data.news.plugin;

import java.util.List;
import java.util.Set;

import com.xt.data.news.dto.NewsDto;
import com.xt.data.news.event.ChannelEventDto;

/**
 * 新闻插件
 * @author vivi207
 *
 */
public interface NewsPlugin<T extends NewsDto> {
	
	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	String getId();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	String getName();

	/**
	 * 获取版本
	 * 
	 * @return 版本
	 */
	String getVersion();

	/**
	 * 是否启用
	 * 
	 * @return 版本
	 */
	boolean enabled();

	/**
	 * 排序
	 * 
	 * @return 排序
	 */
	int orders();
	
	/**
	 * 初始化
	 */
	void init();
	
	/**
	 * 重置
	 */
	void reset();
	
	/**
	 * 获取关联频道插件
	 * 
	 * @return 名称
	 */
	Set<String> getChannelPluginIds();

	/**
	 * 编辑新闻(数据处理)
	 * @param messageDto
	 * @param async
	 */
	List handle(T newsDto);
	
	/**
	 * 触发事件(频道分析)
	 */
	ChannelEventDto trigger(List data);
}
