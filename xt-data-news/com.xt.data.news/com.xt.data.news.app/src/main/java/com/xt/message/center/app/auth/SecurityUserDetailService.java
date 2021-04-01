package com.xt.message.center.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.xt.data.news.auth.JwtAccess;

import lombok.extern.slf4j.Slf4j;

/**
 * 认证查询
 * @author vivi207
 *
 */
@Slf4j
@Component
public class SecurityUserDetailService implements UserDetailsService {
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Value("${xt.message.center.admin.username:admin}")
	private String admin;
	@Value("${xt.message.center.admin.password:123321}")
	private String password;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SecurityUser securityUser = new SecurityUser();
		securityUser.setUsername(admin);
		securityUser.setPassword(passwordEncoder.encode(password));
		securityUser.setRoles(new String[] {"admin"});
		
		return securityUser;
	}
	
	
}
