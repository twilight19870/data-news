package com.xt.data.news.plugin;

import java.util.List;
import java.util.Map;

import com.xt.data.news.consts.Domain;

/**
 * 频道插件
 * @author vivi207
 *
 */
public interface ChannelPlugin {
	
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
	 * 获取领域
	 * @return
	 */
	Domain getDomain();
	
	/**
	 * 获取描述
	 * 
	 * @return 描述
	 */
	String getRemark();
	
	/**
	 * 获取消息模版ID
	 * 
	 * @return 描述
	 */
	String getTemplateId();
	
	
	/**
	 * 排序
	 * 
	 * @return 排序
	 */
	int orders();

	/**
	 * 是否启用
	 * 
	 * @return 版本
	 */
	boolean enabled();

	/**
	 * 获取版本
	 * 
	 * @return 版本
	 */
	String getVersion();

	/**
	 * 初始化
	 */
	void init();
	
	/**
	 * 重置
	 */
	void reset();
	
	/**
	 * 设置属性(后台管理)
	 * @return 设置页面
	 */
	String setting();
	
	/**
	 * 保存设置(后台管理)
	 */
	void save(Map<String, String> attributes);
	
	/**
	 * 获取属性
	 * @param name
	 * @return
	 */
	String getAttribute(String name);
	 
	/**
	 * 获取属性配置
	 * @return
	 */
	public Map<String, String> getAttributes();

	/**
	 * 设置属性配置
	 * @param attributes
	 */
	public void setAttributes(Map<String, String> attributes);
	
	/**
	 * 定制设置(用户)
	 * @return 设置页面
	 */
	String custom();
	
	/**
	 * 保存设置(用户)
	 */
	void saveCustomParams(String customId, Map<String, String> attributes);
	
	/**
	 * 计时器(为空为实时触发)
	 * @return
	 */
	String getTimer();
	
	/**
	 * 触发事件(数据分析)
	 */
	boolean trigger(List data);
	
	/**
	 * 推送新闻(推送订阅)
	 * @param customId 用户标识
	 * @param customParams 用户参数
	 * @return 消息ID
	 */
	String publish(String customId, Map<String, String> customParams);
}
