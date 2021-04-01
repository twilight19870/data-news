package com.xt.data.news.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xt.data.news.utils.DateUtils;

public class FilterUtil {
	private List<Filter> filters;
	
	public static FilterUtil create() {
		return new FilterUtil();
	}
	
	public List<Filter> filters(){
		if(filters==null) {
			return new ArrayList(0);
		}
		return filters;
	}
	
	public FilterUtil add(Filter filter) {
		if(filter==null) {
			return this;
		}
		if(filters==null) {
			filters = new ArrayList();
		}
		filters.add(filter);
		return this;
	}
	
	public FilterUtil ne(String property, Object value) {
		if(value==null||"".equals(value)) {
			return this;
		}
		return add(Filter.ne(property, value));
	}
	
	public FilterUtil eq(String property, Object value) {
		if(value==null||"".equals(value)) {
			return this;
		}
		return add(Filter.eq(property, value));
	}
	
	/**
	 * 大于等于
	 * @param property
	 * @param value
	 * @return
	 */
	public FilterUtil ge(String property, Object value) {
		if(value==null||"".equals(value)) {
			return this;
		}
		return add(Filter.ge(property, value));
	}
	
	/**
	 * 小于等于
	 * @param property
	 * @param value
	 * @return
	 */
	public FilterUtil le(String property, Object value) {
		if(value==null||"".equals(value)) {
			return this;
		}
		return add(Filter.le(property, value));
	}
	
	/**
	 * 小于
	 * @param property
	 * @param value
	 * @return
	 */
	public FilterUtil lt(String property, Object value) {
		if(value==null||"".equals(value)) {
			return this;
		}
		return add(Filter.lt(property, value));
	}
	
	/**
	 * 大于
	 * @param property
	 * @param value
	 * @return
	 */
	public FilterUtil gt(String property, Object value) {
		if(value==null||"".equals(value)) {
			return this;
		}
		return add(Filter.gt(property, value));
	}
	
	public FilterUtil in(String property, Object value) {
		if(value==null||"".equals(value)) {
			return this;
		}
		return add(Filter.in(property, value));
	}
	
	public FilterUtil like(String property, Object value, boolean left, boolean right) {
		if(value==null||"".equals(value)) {
			return this;
		}
		return add(Filter.like(property, (left?"%":"")+value+(right?"%":"")));
	}
	
	public FilterUtil like(String property, Object value) {
		return like(property, value, true, true);
	}
	
	public FilterUtil likeLeft(String property, Object value) {
		return like(property, value, true, false);
	}
	
	public FilterUtil likeRight(String property, Object value) {
		return like(property, value, false, true);
	}
	
	public FilterUtil isNull(String property) {
		return add(Filter.isNull(property));
	}
	
	public FilterUtil isNotNull(String property) {
		return add(Filter.isNotNull(property));
	}
	
	public FilterUtil join(String property, Object value) {
		if(value==null||"".equals(value)) {
			return this;
		}
		return add(Filter.join(property, value));
	}
	
	public FilterUtil notIn(String property, Object value) {
		if(value==null||"".equals(value)) {
			return this;
		}
		if(value instanceof Collection && ((Collection)value).size()<1) {
			return this;
		}
		if(value.getClass().isArray() && ((Object[])value).length<1) {
			return this;
		}
		return add(Filter.notIn(property, value));
	}
	
	/**
	 * or
	 * @param filters
	 * @return
	 */
	public FilterUtil or(Filter... filters) {
		if(filters==null|| filters.length==0) {
			return this;
		}
		return add(Filter.or(filters));
	}
	
	public FilterUtil clear() {
		filters.clear();
		return this;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public FilterUtil addFilter(String property, Date value, Integer days, Filter.Operator operator) {
		if(value!=null && days!=null && days!=0) {
			value = DateUtils.addDateDays(value, days);
		}
		return addFilter(property, value, operator);
	}
	
	public FilterUtil addFilter(String property, Object value, Filter.Operator operator) {
		if(value==null || "".equals(value)) {
			return this;
		}
		if(getFilters()==null) {
			this.setFilters(new ArrayList(8));
		}
		operator = operator==null?Filter.Operator.EQ:operator;
		add(new Filter(property, operator, value));
		return this;
	}
	
	public FilterUtil addFilter(String property, Object value) {
		return this.addFilter(property, value, Filter.Operator.EQ);
	}
	
	public FilterUtil addFilter(String property, Filter.Operator operator) {
		return this.addFilter(property, null, operator);
	}
}
