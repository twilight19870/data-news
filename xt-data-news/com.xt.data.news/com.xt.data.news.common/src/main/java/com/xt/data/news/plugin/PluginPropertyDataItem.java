package com.xt.data.news.plugin;

/**
 * 插件属性数据选项
 * @author vivi207
 *
 */
public @interface PluginPropertyDataItem {

	/**
	 * 值
	 * @return
	 */
	String value();
	
	/**
	 * 名称，默认使用 value
	 * @return
	 */
	String title() default "";
	
}
