package com.xt.data.news.dto;

import java.util.Set;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * 领域对象
 * @author vivi207
 *
 */
@Data
public class DomainDto {
	
	/** 领域ID **/
	protected String id;
	
	/** 领域名称 **/
	protected String name;
	
	/** 适用角色 **/
	protected Set<String> roles;
	
	/** 是否开启 **/
	protected boolean open;
	
	/** 描述 **/
	protected String remark;
	
	/** 排序 **/
	protected int orders;
	

	@Override
	public String toString() {
		return "DomainDto [id=" + id + ", name=" + name + ", roles=" + roles + ", open=" + open + ", remark=" + remark
				+ ", orders=" + orders + "]";
	}
}
