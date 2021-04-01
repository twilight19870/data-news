package com.xt.data.news.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 插件属性集合
 * @author vivi207
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface PluginPropertys {

	/**
	 * 属性集合
	 * @return
	 */
	PluginProperty[] value();
	
}
