package com.xt.data.news.base;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 排序
 * @author xt team
 * @version 0.0.1
 */
public class Order implements Serializable {

	private static final long serialVersionUID = -3078342809727773232L;

	/**
	 * 方向
	 */
	public enum Direction {

		/**
		 * 递增
		 */
		ASC,

		/**
		 * 递减
		 */
		DESC
	}

	/**
	 * 排序
	 * @author vivi207
	 *
	 */
	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface By {
		/** 属性，默认：id **/
		String property() default "id";
		/** 排序方式，默认：desc **/
		Direction direction() default Direction.DESC;
	}

	/**
	 * 默认方向
	 */
	private static final Order.Direction DEFAULT_DIRECTION = Order.Direction.DESC;

	/**
	 * 创建时间倒叙
	 */
	private static final List<Order> createdDateDescOrder = new ArrayList(1) {{
		add(desc("createdDate"));
	}};

	/**
	 * 属性
	 */
	private String property;

	/**
	 * 方向
	 */
	private Order.Direction direction = DEFAULT_DIRECTION;

	/**
	 * 排序开关
	 */
	private boolean orderEnable = true;

	/**
	 * 构造方法
	 */
	public Order() {
	}

	/**
	 * 构造方法
	 *
	 * @param property
	 *            属性
	 * @param direction
	 *            方向
	 */
	public Order(String property, Order.Direction direction) {
		this.property = property;
		this.direction = direction;
	}

	/**
	 * 返回递增排序
	 *
	 * @param property
	 *            属性
	 * @return 递增排序
	 */
	public static Order asc(String property) {
		return new Order(property, Order.Direction.ASC);
	}

	/**
	 * 返回递减排序
	 *
	 * @param property
	 *            属性
	 * @return 递减排序
	 */
	public static Order desc(String property) {
		return new Order(property, Order.Direction.DESC);
	}

	/**
	 * 获取属性
	 *
	 * @return 属性
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * 设置属性
	 *
	 * @param property
	 *            属性
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * 获取方向
	 *
	 * @return 方向
	 */
	public Order.Direction getDirection() {
		return direction;
	}

	/**
	 * 设置方向
	 *
	 * @param direction
	 *            方向
	 */
	public void setDirection(Order.Direction direction) {
		this.direction = direction;
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
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * 重写hashCode方法
	 *
	 * @return HashCode
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public boolean isOrderEnable() {
		return orderEnable;
	}

	public void setOrderEnable(boolean orderEnable) {
		this.orderEnable = orderEnable;
	}

}
