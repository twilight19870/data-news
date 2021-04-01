package com.xt.message.center.app.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xt.data.news.utils.ResultUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Security认证过滤器
 * @author vivi207
 *
 */
@Slf4j
@Component
public class SecurityAuthenticationTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = jwtTokenHelper.readToken(request);
		UsernamePasswordAuthenticationToken authentication = null;
		if(token!=null && !"".equals(token)) {
			authentication = jwtTokenHelper.createAuthentication(token);
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}
