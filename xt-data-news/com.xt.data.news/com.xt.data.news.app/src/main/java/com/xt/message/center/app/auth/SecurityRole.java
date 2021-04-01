package com.xt.message.center.app.auth;

import org.springframework.security.core.GrantedAuthority;

import com.xt.data.news.auth.JwtRole;

/**
 * Security role
 * @author vivi207
 *
 */
public class SecurityRole extends JwtRole implements GrantedAuthority {
	public SecurityRole() {
		super();
	}
	
	public SecurityRole(String authority) {
		super(authority);
	}
	
}
