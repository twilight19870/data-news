package com.xt.data.news.base;

import java.util.ArrayList;
import java.util.List;

public class OrderUtil {
	private List<Order> orders;
	
	public static OrderUtil create() {
		return new OrderUtil();
	}
	
	public OrderUtil asc(String property) {
		if(property==null) {
			return this;
		}
		if(orders==null) {
			orders = new ArrayList(4);
		}
		orders.add(Order.asc(property));
		return this;
	}
	
	public OrderUtil desc(String property) {
		if(property==null) {
			return this;
		}
		if(orders==null) {
			orders = new ArrayList(4);
		}
		orders.add(Order.desc(property));
		return this;
	}
	
	public OrderUtil clear() {
		orders.clear();
		return this;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	public List<Order> orders() {
		return orders;
	}
}
