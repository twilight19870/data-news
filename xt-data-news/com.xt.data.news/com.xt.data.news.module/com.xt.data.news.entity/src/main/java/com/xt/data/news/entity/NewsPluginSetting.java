package com.xt.data.news.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.xt.data.news.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;
 /**
 * Entity - 新闻频道插件设置
 * 
 * @author vivi207
 * @version xt V0.0.1
 * @date 2021-03-31 10:10:20
 */
@Entity
@Table(name="news_plugin_setting")
public class NewsPluginSetting extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;


    /** 插件ID **/
	@JsonView(BaseView.class)
	@Column(name="news_plugin_id")
	@Length(max = 72)
	@NotNull
    private String newsPluginId;

    /** 插件ID **/
	@JsonView(BaseView.class)
	@Column(name="channel_plugin_id")
	@Length(max = 72)
	@NotNull
    private String channelPluginId;

    
	/**
	 * 插件ID
	 * @param newsPluginId
	 */
	public void setNewsPluginId(String newsPluginId){
    	this.newsPluginId = newsPluginId;
    }
    
	/**
     * 插件ID
     * @return
     */
    public String getNewsPluginId(){
    	return this.newsPluginId;
    }
    
	/**
	 * 插件ID
	 * @param channelPluginId
	 */
	public void setChannelPluginId(String channelPluginId){
    	this.channelPluginId = channelPluginId;
    }
    
	/**
     * 插件ID
     * @return
     */
    public String getChannelPluginId(){
    	return this.channelPluginId;
    }
}