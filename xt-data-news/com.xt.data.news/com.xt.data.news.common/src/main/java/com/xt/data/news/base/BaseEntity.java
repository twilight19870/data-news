package com.xt.data.news.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Entity - 基类
 * 
 * @author xt team
 * @version 0.0.1
 */
@EntityListeners({})
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = -67188388306700736L;
	
	/**
	 * "ID"属性名称
	 */
	public static final String ID_PROPERTY_NAME = "id";

	/**
	 * "创建日期"属性名称
	 */
	public static final String CREATED_DATE_PROPERTY_NAME = "createdTime";

	/**
	 * "最后修改日期"属性名称
	 */
	public static final String LAST_MODIFIED_DATE_PROPERTY_NAME = "modifyTime";

	/**
	 * "版本"属性名称
	 */
	public static final String VERSION_PROPERTY_NAME = "version";

	/**
	 * "删除标记"属性名称
	 */
	public static final String DEL_FALG_NAME = "delFlag";
	
	/**
	 * 属性集合
	 */
	public static final String[] FIELDS = new String[] {ID_PROPERTY_NAME, CREATED_DATE_PROPERTY_NAME, LAST_MODIFIED_DATE_PROPERTY_NAME, VERSION_PROPERTY_NAME, DEL_FALG_NAME};

	/**
	 * 保存验证组
	 */
	public interface Save extends Default {

	}

	/**
	 * 更新验证组
	 */
	public interface Update extends Default {

	}

	/**
	 * 必要视图
	 */
	public interface RequiredView {

	}

	/**
	 * 基础视图
	 */
	public interface BaseView extends RequiredView{

	}
	
	/**
	 * 列表视图
	 */
	public interface ListView extends BaseView{

	}
	
	/**
	 * 简单视图
	 */
	public interface SimpleView extends RequiredView{

	}
	
	/**
	 * 全部视图
	 */
	public interface FullView extends RequiredView{

	}
	
	/**
	 * 详情视图
	 */
	public interface DetailView extends ListView{

	}
	
	/**
	 * ID
	 */
	@JsonView(value = {RequiredView.class})
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ID id;

	/**
	 * 创建日期
	 */
	@JsonView(value = {RequiredView.class})
	@CreatedDate
	@Column(name = "created_time", nullable = false, updatable = false)
	private Date createdTime;

	/**
	 * 最后修改日期
	 */
	@JsonView(value = {DetailView.class})
	@LastModifiedDate
	@Column(name = "modify_time", nullable = false)
	private Date modifyTime;

	/**
	 * 版本
	 */
	@Version
	@Column(nullable = false)
	private Long version;
	
	/**
	 *  删除标志
	 */
	@Column(name="del_flag")
	@NotNull
    private Boolean delFlag;

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public ID getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(ID id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * 获取版本
	 * 
	 * @return 版本
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * 设置版本
	 * 
	 * @param version
	 *            版本
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * 判断是否为新建对象
	 * 
	 * @return 是否为新建对象
	 */
	@Transient
	public boolean isNew() {
		return getId() == null;
	}

	/**
	 * 重写toString方法
	 * 
	 * @return 字符串
	 */
	@Override
	public String toString() {
		return String.format("Entity of type %s with id: %s", getClass().getName(), getId());
	}

	/**
	 * 重写equals方法
	 * 
	 * @param obj
	 *            对象
	 * @return 是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		BaseEntity<?> other = (BaseEntity<?>) obj;
		return getId() != null ? getId().equals(other.getId()) : false;
	}

	/**
	 * 重写hashCode方法
	 * 
	 * @return HashCode
	 */
	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += getId() != null ? getId().hashCode() * 31 : 0;
		return hashCode;
	}

	/**
	 * 保存前处理
	 * 
	 * @param entity
	 *            实体对象
	 */
	@SuppressWarnings("unchecked")
	@PrePersist
	public void prePersist() {
		Date currTime = new Date();
		if(getCreatedTime()==null) {
			setCreatedTime(currTime);
		}
		if(getModifyTime()==null) {
			setModifyTime(currTime);
		}
		if(getVersion()==null) {
			setVersion(1L);
		}
		if(getDelFlag()==null) {
			setDelFlag(Boolean.FALSE);
		}
		
	}

	/**
	 * 更新前处理
	 * 
	 * @param entity
	 *            实体对象
	 */
	@PreUpdate
	public void preUpdate() {
		Date currTime = new Date();
		if(getModifyTime()==null) {
			setModifyTime(currTime);
		}
	}
}