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
 * Entity - 用户
 * 
 * @author vivi207
 * @version xt V0.0.1
 * @date 2021-03-31 10:10:20
 */
@Entity
@Table(name="users")
public class Users extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;
    /**
	 * 状态,0禁用，1启用
	 */
	public enum Status {
		/**
		 * 禁用
		 */
		STATUS0("禁用")
				/**
		 * 启用
		 */
		,STATUS1("启用")
		;
		
		/** 标题 **/
		String title;
		
		Status(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		
		public static Status titleOf(String title) {
			for(Status s : Status.values()) {
				if(s.getTitle().equals(title)) {
					return s;
				}
			}
			return null;
		}
	}


    /** 用户账号 **/
	@JsonView(BaseView.class)
	@Length(max = 72)
	@NotNull
    private String username;

    /** 名称 **/
	@JsonView(BaseView.class)
	@Length(max = 72)
    private String name;

    /** 用户密码 **/
	@JsonView(BaseView.class)
	@Length(max = 72)
	@NotNull
    private String password;

    /** 用户类型,admin,user **/
	@JsonView(BaseView.class)
	@Length(max = 36)
	@NotNull
    private String type;

    /** 手机号码 **/
	@JsonView(BaseView.class)
	@Length(max = 36)
    private String mobile;

    /** 邮箱地址 **/
	@JsonView(BaseView.class)
	@Length(max = 72)
    private String email;

    /** 角色 **/
	@JsonView(BaseView.class)
	@Length(max = 128)
    private String roles;

    /** 状态,0禁用，1启用 **/
	@JsonView(BaseView.class)
	@NotNull
    private Integer status;

    
	/**
	 * 用户账号
	 * @param username
	 */
	public void setUsername(String username){
    	this.username = username;
    }
    
	/**
     * 用户账号
     * @return
     */
    public String getUsername(){
    	return this.username;
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
	 * 用户密码
	 * @param password
	 */
	public void setPassword(String password){
    	this.password = password;
    }
    
	/**
     * 用户密码
     * @return
     */
    public String getPassword(){
    	return this.password;
    }
    
	/**
	 * 用户类型,admin,user
	 * @param type
	 */
	public void setType(String type){
    	this.type = type;
    }
    
	/**
     * 用户类型,admin,user
     * @return
     */
    public String getType(){
    	return this.type;
    }
    
	/**
	 * 手机号码
	 * @param mobile
	 */
	public void setMobile(String mobile){
    	this.mobile = mobile;
    }
    
	/**
     * 手机号码
     * @return
     */
    public String getMobile(){
    	return this.mobile;
    }
    
	/**
	 * 邮箱地址
	 * @param email
	 */
	public void setEmail(String email){
    	this.email = email;
    }
    
	/**
     * 邮箱地址
     * @return
     */
    public String getEmail(){
    	return this.email;
    }
    
	/**
	 * 角色
	 * @param roles
	 */
	public void setRoles(String roles){
    	this.roles = roles;
    }
    
	/**
     * 角色
     * @return
     */
    public String getRoles(){
    	return this.roles;
    }
    
	/**
	 * 状态,0禁用，1启用
	 * @param status
	 */
	public void setStatus(Integer status){
    	this.status = status;
    }
    
	/**
     * 状态,0禁用，1启用
     * @return
     */
    public Integer getStatus(){
    	return this.status;
    }
}