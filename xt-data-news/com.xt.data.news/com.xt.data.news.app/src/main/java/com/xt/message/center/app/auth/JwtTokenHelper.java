package com.xt.message.center.app.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xt.data.news.auth.JwtConfig;

import lombok.Data;

/**
 * jwt 帮助类
 * @author vivi207
 *
 */
@Data
@Component
public class JwtTokenHelper {
	private final static Logger LOGGER = LoggerFactory.getLogger(JwtTokenHelper.class);
	
	@Autowired
	private JwtConfig jwtConfig;
	
	/**
	 * 读取token
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public String readToken(NativeWebRequest request) throws UnsupportedEncodingException, IOException {
		LOGGER.debug("get jwt header key:{}", jwtConfig.getHeaderKey());
		String authorization = request.getHeader(jwtConfig.getHeaderKey());
		
		return readToken(authorization);
	}
	
	/**
	 * 读取token
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public String readToken(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		LOGGER.debug("get jwt header key:{}", jwtConfig.getHeaderKey());
		String token = request.getParameter(jwtConfig.getHeaderKey());
		String authorization = request.getHeader(jwtConfig.getHeaderKey());
		if(token != null && authorization == null){
			authorization = token;
		}
		return readToken(authorization);
	}
	
	/**
	 * 读取token
	 * @param authorization
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public String readToken(String authorization) throws UnsupportedEncodingException, IOException {
		if (authorization != null && authorization.startsWith(jwtConfig.getHeaderPrefix())) {
			String token = authorization.substring(jwtConfig.getHeaderPrefix().length());
			return token;
		}
		return null;
	}
	
	private Algorithm algorithm;
	
	public Algorithm getAlgorithm() {
		if(algorithm==null) {
			algorithm = Algorithm.HMAC512(jwtConfig.getSecretKey());
		}
		return algorithm;
	}
	
	/**
	 * 创建身份认证
	 * @param token
	 * @return
	 */
	public UsernamePasswordAuthenticationToken createAuthentication(String token) {
		SecurityUser access = verify(token);
		
		Collection<? extends GrantedAuthority> authorities = access.getAuthorities()==null?new ArrayList(0):access.getAuthorities().stream().map(i -> new SimpleGrantedAuthority(i.getAuthority())) .collect(Collectors.toList());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(access, "", authorities);
        return authentication;
	}
	
	/**
	 * 创建token
	 * @param access
	 * @return
	 */
	public String generate(SecurityUser access) {
		String token = JWT.create()
				.withIssuer(jwtConfig.getIssuer())
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
				.withSubject(access.getUsername())
				.withClaim("id", access.getId())
				.withClaim("username", access.getUsername())
				.withClaim("name", access.getName())
				.withClaim("city", access.getCityId())
				.withClaim("type", access.getType())
				.withClaim("isEnabled", access.isEnabled())
				.withClaim("roles", Arrays.asList(access.getRoles().toArray()))
				.withClaim("_extData", access.get_extData())
				.sign(getAlgorithm());
		return token;
	}
	
	/**
	 * 校验解析
	 * @param token
	 * @return
	 */
	public SecurityUser verify(String token) {
		JWTVerifier verifier = JWT.require(getAlgorithm())
		        .withIssuer(jwtConfig.getIssuer())
		        .build();
		DecodedJWT jwt = verifier.verify(token);
		Map<String, Claim> claims = jwt.getClaims();
		
		SecurityUser access = new SecurityUser();
		access.setId(claims.containsKey("id")? claims.get("id").asLong() : null);
		access.setUsername(claims.containsKey("username")? claims.get("username").asString() : null);
		access.setName(claims.containsKey("name")? claims.get("name").asString() : null);
		access.setCityId(claims.containsKey("cityId")? claims.get("cityId").asLong() : null);
		access.setType(claims.containsKey("type")? claims.get("type").asString() : null);
		access.setIsEnabled(claims.containsKey("isEnabled")? claims.get("isEnabled").asBoolean() : null);
		access.set_extData(claims.containsKey("extData")? claims.get("extData").asMap() : null);
		access.setRoles(claims.containsKey("roles")?claims.get("roles").asArray(String.class):null);
		return access;
	}
	
	/**
	 * 跳过不验证路径
	 * @return
	 */
	public String[] permitAll() {
		String p = jwtConfig.getPermitAll();
		if(p==null || "".equals(p)) {
			return new String[]{};
		}
		return p.split(",");
	}
}
