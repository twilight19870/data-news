package com.xt.data.news.dto;

import java.util.Set;

import lombok.Data;

/**
 * 频道对象
 * @author vivi207
 *
 */
@Data
public class ChannelDto {
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
	
	/** 所属领域 **/
	protected DomainDto domain;
	
	@Override
	public String toString() {
		return "ChannelDto [id=" + id + ", name=" + name + ", roles=" + roles + ", open=" + open + ", remark=" + remark
				+ ", orders=" + orders + ", domain=" + domain + "]";
	}
}
