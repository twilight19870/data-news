package com.xt.data.news.auth;

import java.io.Serializable;
import java.util.Set;

/**
 * 基础账号
 * @author vivi207
 *
 */
public interface BaseAccess extends Serializable {
	
	Long getId();
	
	String getPassword();
	
	String getUsername();
	
	boolean isEnabled();
	
	boolean isLocked();
	
	Set<String> getRoles();
}
