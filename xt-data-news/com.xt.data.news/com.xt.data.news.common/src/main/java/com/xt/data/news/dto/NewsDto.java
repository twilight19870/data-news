package com.xt.data.news.dto;

import java.util.List;

import lombok.Data;

/**
 * 消息对象
 * @author vivi207
 *
 */
@Data
public class NewsDto<T> {
	
	/**
	 * 时间戳
	 */
	protected Long timestamp;
	/**
	 * 签名
	 */
	protected String sign;
	
	/** 消息ID **/
	protected String id;
	
	/** 消息类型 **/
	protected String type;
	
	/** 标题 **/
	protected String title;
	
	/** 数据 **/
	protected List<T> data;
	
	/** 附件 **/
	protected String attachs;
	
	@Override
	public String toString() {
		return "NewsDto [timestamp=" + timestamp + ", sign=" + sign + ", id=" + id + ", type=" + type + ", title="
				+ title + ", attachs=" + attachs + "]";
	}
}
