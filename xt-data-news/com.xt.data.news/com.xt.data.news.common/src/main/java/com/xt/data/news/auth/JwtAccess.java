package com.xt.data.news.auth;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * Jwt 账号
 * @author vivi207
 *
 */
@Data
public class JwtAccess<T extends JwtRole> implements BaseAccess {
	/** 账号ID，主键ID **/
	protected Long id;
	
	/** 账号 **/
	protected String username;
	
	/** 密码 **/
	protected String password;
	
	/** 拥有角色 **/
	private List<T> authorities;
	
	/** 账号名称 **/
	protected String name;
	
	/** 账户关闭 **/
	protected boolean isAccountNonExpired = true;
	
	/** 账户锁定 **/
	protected boolean isAccountNonLocked = true;
	
	/** 账户凭证关闭 **/
	protected boolean isCredentialsNonExpired = true;
	
	/** 账户可用 **/
	protected Boolean isEnabled = true;
	
	/** 城市ID **/
	protected Long cityId;
	
	/** 账户类型 **/
	protected String type;
	
	/** 扩展信息 **/
	protected Map<String, Object> _extData;

	public JwtAccess() {
		super();
	}

	public JwtAccess(String username, List<T> authorities) {
		super();
		this.username = username;
		this.authorities = authorities;
	}
	
	public JwtAccess(String username, String name, List<T> authorities) {
		super();
		this.username = username;
		this.name = name;
		this.authorities = authorities;
	}
	
	public JwtAccess(Long id, String username, List<T> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.authorities = authorities;
	}
	
	public JwtAccess(Long id, String username, String name, List<T> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.authorities = authorities;
	}
	
	public JwtAccess(Long id,String username, List<T> authorities, boolean isEnabled) {
		super();
		this.id = id;
		this.username = username;
		this.authorities = authorities;
		this.isEnabled = isEnabled;
	}
	
	public JwtAccess(Long id,String username, String name, List<T> authorities, boolean isEnabled) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.authorities = authorities;
		this.isEnabled = isEnabled;
	}
	
	public JwtAccess(Long id, String username, String password, List<T> authorities, boolean isAccountNonExpired,
			boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
	}
	
	public JwtAccess(Long id, String username, String name, String password, List<T> authorities, boolean isAccountNonExpired,
			boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.authorities = authorities;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "JwtAccess [id=" + id + ", username=" + username + ", password=" + password + ", authorities="
				+ authorities + ", name=" + name + ", isAccountNonExpired=" + isAccountNonExpired
				+ ", isAccountNonLocked=" + isAccountNonLocked + ", isCredentialsNonExpired=" + isCredentialsNonExpired
				+ ", isEnabled=" + isEnabled + ", cityId=" + cityId + ", type=" + type + ", _extData=" + _extData + "]";
	}

	@Override
	public boolean isLocked() {
		return isAccountNonLocked();
	}

	@Override
	public Set<String> getRoles() {
		if(getAuthorities()==null) {
			return new HashSet(0);
		}
		return getAuthorities().stream().map(i->i.getAuthority()).collect(Collectors.toSet());
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}
	
	public void setRoles(String jsonArr) {
		if(jsonArr==null || "".equals(jsonArr) || jsonArr.charAt(0) != '[') {
			return;
		}
		if("[]".equals(jsonArr) || jsonArr.charAt(1)=='{') {
			List<JwtRole> jwtRoles = JSONUtil.parseArray(jsonArr).toList(JwtRole.class);
			setAuthorities((List<T>) jwtRoles);
		}else {
			List<String> roles = JSONUtil.parseArray(jsonArr).toList(String.class);
			setAuthorities((List<T>) roles.stream().map(i->new JwtRole(i)).collect(Collectors.toList()));
		}
	}
	
	public void setRoles(String[] roles) {
		if(roles==null) {
			return;
		}
		setAuthorities((List<T>) Arrays.asList(roles).stream().map(i->new JwtRole(i)).collect(Collectors.toList()));
	}
	
	public Map<String, Object> get_extData() {
		return _extData;
	}

	public void set_extData(Map<String, Object> _extData) {
		this._extData = _extData;
	}
	
	public Object extData(String key) {
		if(get_extData()==null) {
			return null;
		}
		return get_extData().get(key);
	}

	public JwtAccess extData(String key, Object value) {
		if(get_extData()==null) {
			set_extData(new HashMap());
		}
		get_extData().put(key, value);
		return null;
	}
	
	public List<T> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<T> authorities) {
		this.authorities = authorities;
	}
 
	
}
