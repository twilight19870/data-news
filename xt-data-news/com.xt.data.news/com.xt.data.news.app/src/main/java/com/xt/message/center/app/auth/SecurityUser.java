package com.xt.message.center.app.auth;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;

import com.xt.data.news.auth.JwtAccess;

/**
 * Security 认证对象 
 * @author vivi207
 *
 */
public class SecurityUser extends JwtAccess<SecurityRole> implements UserDetails {
	private static final long serialVersionUID = -5367678599268023239L;
	
	/** 拥有角色 **/
	private List<SecurityRole> authorities;

	public List<SecurityRole> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<SecurityRole> authorities) {
		this.authorities = authorities;
	}
 
	public void setRoles(String[] roles) {
		if(roles==null) {
			return;
		}
		setAuthorities(Arrays.asList(roles).stream().map(i->new SecurityRole(i)).collect(Collectors.toList()));
	}
	
 

}
