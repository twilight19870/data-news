package com.xt.message.center.app.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xt.message.center.app.resolver.CurrUserArgumentResolver;

/**
 * 会话注解加载配置
 * @author vivi207
 *
 */
@Configuration
public class SessionAnnotationLoadConfig implements WebMvcConfigurer {
	
	@Autowired
	private CurrUserArgumentResolver currUserArgumentResolver;
	 
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currUserArgumentResolver);
	}
}
