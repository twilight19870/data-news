package com.xt.data.news.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * jwt 配置
 * @author vivi207
 *
 */
@Data
@ConfigurationProperties(prefix = "xt.auth.jwt")
@Configuration
public class JwtConfig {
	/** jwt 安全Key  **/
	private String secretKey;
	
	/** jwt header Key，默认：Authorization  **/
	private String headerKey;
	
	/** jwt header 前缀，默认：Bearer  **/
	private String headerPrefix;
	
	/** jwt 超时时间，默认：30分钟  **/
	private long expiration;
	
	/** jwt 拦截跳过请求  **/
	private String permitAll;
	
	/** jwt 令牌刷新时间，单位：秒，默认：0  **/
	private long refreshTime;
	
	/** jwt 令牌宽限时间，单位：秒，默认：0  **/
	private long expirationExpand;
	
	/** jwt 发行人 **/
	private String issuer;
	
	/** jwt 是否校验,网关模式下，不用校验直接取值 **/
	private Boolean valid;
	

	@Override
	public String toString() {
		return "JwtConfig [secretKey=" + secretKey + ", headerKey=" + headerKey + ", headerPrefix=" + headerPrefix
				+ ", expiration=" + expiration + ", permitAll=" + permitAll + ", refreshTime=" + refreshTime
				+ ", expirationExpand=" + expirationExpand + ", issuer=" + issuer + ", valid=" + valid + "]";
	}
}
