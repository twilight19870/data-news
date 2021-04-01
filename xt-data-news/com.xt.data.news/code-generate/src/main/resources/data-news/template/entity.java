package ${Curr.package};<#include "include/hasOrders.ftl" /><#include "include/hasStatus.ftl" />

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
import ${Config['basePackage']}.base.BaseEntity;
<#if (Config.jsonView)?? &&Config.jsonView=='open'>
import com.fasterxml.jackson.annotation.JsonView;
</#if>
<#include "include/swagger_packages_model.ftl"/>

<#assign dataTypes = Util.map.arrayByKey(table.columns, "dataType") />
<#assign dataTypes2 = Util.jdbc.toJdbcClassByTypes(dataTypes) />
<#list dataTypes2 as dataType>
<#if dataType?? && dataType!='' && (dataType?index_of("java.lang.") == -1)>
import ${dataType};
</#if>
</#list>
<#include "include/setId.ftl" />
 /**
 * Entity - ${tableComment}
 * 
 * @author ${Config.author}
 * @version ${Config.version}
 * @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
@Entity
@Table(name="${table.tableName}")
<#include "include/swagger_model.ftl"/>
public class ${Data["entity.java"]} extends ${hasOrders?string('OrderedEntity','BaseEntity')}<${idClass!'String'}>{

	private static final long serialVersionUID = 1L;
	<#if hasStatus>
    /**
	 * ${statusComment}
	 */
	public enum Status {
		<#list statusList as s>
		/**
		 * ${s[1]!}
		 */
		${m!}STATUS${s[0]}("${s[1]}")<#assign m=",">
		</#list>;
		
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
	</#if><#assign skipColums = ["id", "version", "created_time", "modify_time", "del_flag", "orders"]>

	<#list table.columns as col>
	<#if !(skipColums?seq_contains(col.columnName)) >

    /** <#if col.columnComment??>${col.columnComment}</#if> **/<#assign fieldName=Util.string.toFiledName(col.columnName, 0 , true)><#assign fieldClassName=Util.string.last(Util.jdbc.toJdbcClassByType(col.dataType),"\\.")>
    <#include "include/jsonView.ftl"/>
	<#include "include/swagger_field.ftl"/>
    <#include "include/jpaAnnotations.ftl" />
    private ${fieldClassName} ${fieldName}<#include "include/defaultValue.ftl" />;
    </#if>
	</#list>

    <#list table.columns as col>
	<#if !(skipColums?seq_contains(col.columnName)) >
    
	/**
	 * <#if col.columnComment??>${col.columnComment}</#if><#assign fieldName=Util.string.toFiledName(col.columnName, 0 , true)><#assign fieldType=Util.string.toMethodName(col.columnName, true)><#assign fieldClassName=Util.string.last(Util.jdbc.toJdbcClassByType(col.dataType),"\\.")>
	 * @param ${fieldName}
	 */
	public void set${fieldType}(${fieldClassName} ${fieldName}){
    	this.${fieldName} = ${fieldName};
    }
    
	/**
     * <#if col.columnComment??>${col.columnComment}</#if>
     * @return
     */
    public ${fieldClassName} get${fieldType}(){
    	return this.${fieldName};
    }
    </#if>
    </#list>
}