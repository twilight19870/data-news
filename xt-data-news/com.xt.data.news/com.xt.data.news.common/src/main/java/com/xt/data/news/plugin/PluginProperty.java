package com.xt.data.news.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 插件属性
 * @author vivi207
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface PluginProperty {

	/**
	 * 属性名称 默认：方法名去get
	 * @return
	 */
	String name() default "";
	
	/**
	 * 属性标题,默认 方法名去get
	 * @return
	 */
	String value() default "";
	
	/**
	 * 默认值
	 * @return
	 */
	String defValue() default "";
	
	/**
	 * 必填， 默认：true
	 * @return
	 */
	boolean required() default true;
	
	/**
	 * 属性数据类型 默认：字符串
	 * @return
	 */
	PluginPropertyDataType dataType() default PluginPropertyDataType.string;
	
	/**
	 * 属性数据选项, select radio时使用
	 * @return
	 */
	PluginPropertyDataItem[] dataItems() default {};
	
	/**
	 * 属性排序 默认：1
	 * @return
	 */
	int order()  default 1;
	
	/**
	 * 最长长度,默认 128
	 * @return
	 */
	int maxlength() default 128;
	
	/**
	 * 校验表达式
	 * @return
	 */
	String valid() default "";
	
	/**
	 * 格式化
	 * @return
	 */
	String format() default "";
}
