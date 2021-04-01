package com.xt.message.center.app.api;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xt.data.news.auth.JwtAccess;
import com.xt.data.news.auth.JwtRole;
import com.xt.message.center.app.auth.JwtTokenHelper;
import com.xt.message.center.app.auth.SecurityUser;

import lombok.extern.slf4j.Slf4j;

/**
 * 登陆接口
 * @author vivi207
 *
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

	@Inject
    private AuthenticationManager authenticationManager;
	
	@Inject
    private JwtTokenHelper jwtTokenHelper;
	
	@PostMapping
	public Map login(String username, String password) {
		//验证用户信息
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        
        Map data = new HashMap();
        data.put("id", user.getId());
        data.put("name", user.getName());
        data.put("username", username);
        data.put("token", jwtTokenHelper.generate(user));
        data.put("headerKey", jwtTokenHelper.getJwtConfig().getHeaderKey());
        data.put("headerPrefix", jwtTokenHelper.getJwtConfig().getHeaderPrefix());
        return data;
	}
}
