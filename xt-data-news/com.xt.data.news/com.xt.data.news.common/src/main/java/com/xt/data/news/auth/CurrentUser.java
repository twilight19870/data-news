package com.xt.data.news.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Security - 当前用户注解
 * @author xt team
 * @version 0.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.PARAMETER)
public @interface CurrentUser {
	
	/**
	 * 从数据刷新
	 * @return
	 */
	boolean refresh() default false;
}