package com.xt.data.news.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilterForm {
	private List<Filter> filters;
	
	private List<Order> orders;
	
	public List<Filter> getFilters() {
		return filters;
	}
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public FilterForm addFilter(String property, Object value, Filter.Operator operator) {
		if(value==null || "".equals(value)) {
			return this;
		}
		if(getFilters()==null) {
			this.setFilters(new ArrayList(8));
		}
		operator = operator==null?Filter.Operator.EQ:operator;
		this.getFilters().add(new Filter(property, operator, value));
		return this;
	}
	
	public FilterForm addFilter(String property, Object value) {
		return this.addFilter(property, value, Filter.Operator.EQ);
	}

	public FilterForm addFilter(String property, Filter.Operator operator) {
		return this.addFilter(property, null, operator);
	}
	
	public FilterForm addBetween(String property, Date date) {
		if(date==null) {
			return this;
		}
		addFilter(property, date, Filter.Operator.GE);
		addFilter(property, new Date(date.getTime()+86400000L), Filter.Operator.LT);
		return this;
	}

	
	public FilterForm asc(String property) {
		if(property==null || "".equals(property)) {
			return this;
		}
		if(getOrders()==null) {
			setOrders(new ArrayList(6));
		}
		getOrders().add(Order.asc(property));
		
		return this;
	}
	
	public FilterForm asc(int index, String property) {
		if(property==null || "".equals(property)) {
			return this;
		}
		if(getOrders()==null) {
			setOrders(new ArrayList(6));
		}
		getOrders().add(index, Order.asc(property));
		
		return this;
	}

	public FilterForm desc(String property) {
		if(property==null || "".equals(property)) {
			return this;
		}
		if(getOrders()==null) {
			setOrders(new ArrayList(6));
		}
		getOrders().add(Order.desc(property));
		
		return this;
	}
	
	public FilterForm desc(int index, String property) {
		if(property==null || "".equals(property)) {
			return this;
		}
		if(getOrders()==null) {
			setOrders(new ArrayList(6));
		}
		getOrders().add(index, Order.desc(property));
		
		return this;
	}
}
