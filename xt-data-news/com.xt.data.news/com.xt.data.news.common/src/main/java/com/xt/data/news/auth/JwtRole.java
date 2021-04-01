package com.xt.data.news.auth;

import lombok.Data;

/**
 * jwt角色
 * @author vivi207
 *
 */
@Data
public class JwtRole {
	/** 角色ID **/
	protected String id;
	
	/** 角色名称 **/
	protected String name;
	
	/** 角色代码 **/
	protected String authority;
	
	public JwtRole() {
		super();
	}
	
	public JwtRole(String authority) {
		super();
		this.authority = authority;
	}
	
	public JwtRole(String id, String name, String authority) {
		super();
		this.id = id;
		this.name = name;
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "JwtRole [id=" + id + ", name=" + name + ", authority=" + authority + "]";
	}

}
