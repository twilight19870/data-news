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
 * Entity - 频道插件设置
 * 
 * @author vivi207
 * @version xt V0.0.1
 * @date 2021-03-31 10:10:20
 */
@Entity
@Table(name="channel_plugin_setting")
public class ChannelPluginSetting extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;


    /** 插件ID **/
	@JsonView(BaseView.class)
	@Column(name="channel_plugin_id")
	@Length(max = 72)
	@NotNull
    private String channelPluginId;

    /** 名称 **/
	@JsonView(BaseView.class)
	@Length(max = 72)
	@NotNull
    private String name;

    /** 领域 **/
	@JsonView(BaseView.class)
	@NotNull
    private Integer domain;

    /** 消息模版ID **/
	@JsonView(BaseView.class)
	@Column(name="template_id")
	@Length(max = 72)
    private String templateId;

    /** 描述 **/
	@JsonView(BaseView.class)
	@Length(max = 4000)
    private String remark;

    /** 是否启用 **/
	@JsonView(BaseView.class)
	@NotNull
    private Integer enabled;

    /** 属性配置 **/
	@Lob
	@NotNull
    private String attributes;

    /** 计时器 **/
	@JsonView(BaseView.class)
	@Length(max = 36)
	@NotNull
    private String timer;

    
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
    
	/**
	 * 名称
	 * @param name
	 */
	public void setName(String name){
    	this.name = name;
    }
    
	/**
     * 名称
     * @return
     */
    public String getName(){
    	return this.name;
    }
    
	/**
	 * 领域
	 * @param domain
	 */
	public void setDomain(Integer domain){
    	this.domain = domain;
    }
    
	/**
     * 领域
     * @return
     */
    public Integer getDomain(){
    	return this.domain;
    }
    
	/**
	 * 消息模版ID
	 * @param templateId
	 */
	public void setTemplateId(String templateId){
    	this.templateId = templateId;
    }
    
	/**
     * 消息模版ID
     * @return
     */
    public String getTemplateId(){
    	return this.templateId;
    }
    
	/**
	 * 描述
	 * @param remark
	 */
	public void setRemark(String remark){
    	this.remark = remark;
    }
    
	/**
     * 描述
     * @return
     */
    public String getRemark(){
    	return this.remark;
    }
    
	/**
	 * 是否启用
	 * @param enabled
	 */
	public void setEnabled(Integer enabled){
    	this.enabled = enabled;
    }
    
	/**
     * 是否启用
     * @return
     */
    public Integer getEnabled(){
    	return this.enabled;
    }
    
	/**
	 * 属性配置
	 * @param attributes
	 */
	public void setAttributes(String attributes){
    	this.attributes = attributes;
    }
    
	/**
     * 属性配置
     * @return
     */
    public String getAttributes(){
    	return this.attributes;
    }
    
	/**
	 * 计时器
	 * @param timer
	 */
	public void setTimer(String timer){
    	this.timer = timer;
    }
    
	/**
     * 计时器
     * @return
     */
    public String getTimer(){
    	return this.timer;
    }
}