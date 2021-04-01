package com.xt.data.news.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 分页信息
 * @author xt team
 * @version 0.0.1
 */
public class Pageable implements Serializable {

	private static final long serialVersionUID = -3930180379790344299L;

	/**
	 * 默认页码
	 */
	private static final int DEFAULT_PAGE_NUMBER = 1;

	/**
	 * 默认每页记录数
	 */
	private static final int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 最大每页记录数
	 */
	private static final int MAX_PAGE_SIZE = 9999;

	/**
	 * 页码
	 */
	private int pageNumber = DEFAULT_PAGE_NUMBER;

	/**
	 * 每页记录数
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;
	
    /**
     * 查询模型
     */
    public enum SearchModel {

        /**  全包围查询 */
        ALL("包含"),
        /** 左查询*/
        LEFT("左包含"),
        /**  右查询 */
        RIGHT("右包含"),
        /**  直接匹配 */
        EQUALS("等于")
        ;
    	private String title;
    	
    	SearchModel(String  title){
    		this.title = title;
    	}
    	
        public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public static String val(SearchModel searchModel, String value) {
        	searchModel = searchModel==null?SearchModel.ALL:searchModel;
        	switch (searchModel) {
			case ALL:
				return "%"+value+"%";
			case LEFT:
				return value+"%";
			case RIGHT:
				return "%"+value;
			case EQUALS:
				return value;
			default:
				return "%"+value+"%";
			}
        }
    }

	/**
	 * 搜索属性
	 */
	private String searchProperty;

	/**
	 * 搜索值
	 */
	private String searchValue;
	
	/**
	 * 搜索值
	 */
	private SearchModel searchModel;

	/**
	 * 搜索开关
	 */
	private boolean searchEnable = true;
	
	/**
	 * 排序开关
	 */
	private boolean orderEnable = true;

	/**
	 * 排序属性
	 */
	private String orderProperty;

	/**
	 * 排序方向
	 */
	private Order.Direction orderDirection;

	/**
	 * 筛选
	 */
	private List<Filter> filters = new ArrayList<>();

	/**
	 * 排序
	 */
	private List<Order> orders = new ArrayList<>();

	/**
	 * 构造方法
	 */
	public Pageable() {
	}

	/**
	 * 构造方法
	 * 
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            每页记录数
	 */
	public Pageable(Integer pageNumber, Integer pageSize) {
		if (pageNumber != null && pageNumber >= 1) {
			this.pageNumber = pageNumber;
		}
		if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
			this.pageSize = pageSize;
		}
	}

	/**
	 * 获取页码
	 * 
	 * @return 页码
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * 设置页码
	 * 
	 * @param pageNumber
	 *            页码
	 */
	public void setPageNumber(int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		this.pageNumber = pageNumber;
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 强行设置每页记录数
	 * 
	 * @param pageSize
	 *            每页记录数
	 */
	public void forceSetPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 设置每页记录数
	 * 
	 * @param pageSize
	 *            每页记录数
	 */
	public void setPageSize(int pageSize) {
		if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	/**
	 * 获取搜索属性
	 * 
	 * @return 搜索属性
	 */
	public String getSearchProperty() {
		return searchProperty;
	}

	/**
	 * 设置搜索属性
	 * 
	 * @param searchProperty
	 *            搜索属性
	 */
	public void setSearchProperty(String searchProperty) {
		this.searchProperty = searchProperty;
	}

	/**
	 * 获取搜索值
	 * 
	 * @return 搜索值
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * 设置搜索值
	 * 
	 * @param searchValue
	 *            搜索值
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public SearchModel getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}

	/**
	 * 获取排序属性
	 * 
	 * @return 排序属性
	 */
	public String getOrderProperty() {
		return orderProperty;
	}

	/**
	 * 设置排序属性
	 * 
	 * @param orderProperty
	 *            排序属性
	 */
	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

	/**
	 * 获取排序方向
	 * 
	 * @return 排序方向
	 */
	public Order.Direction getOrderDirection() {
		return orderDirection;
	}

	/**
	 * 设置排序方向
	 * 
	 * @param orderDirection
	 *            排序方向
	 */
	public void setOrderDirection(Order.Direction orderDirection) {
		this.orderDirection = orderDirection;
	}

	/**
	 * 获取筛选
	 * 
	 * @return 筛选
	 */
	public List<Filter> getFilters() {
		return filters;
	}

	/**
	 * 设置筛选
	 * 
	 * @param filters
	 *            筛选
	 */
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * 设置排序
	 * 
	 * @param orders
	 *            排序
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
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

	public boolean isSearchEnable() {
		return searchEnable;
	}

	public void setSearchEnable(boolean searchEnable) {
		this.searchEnable = searchEnable;
	}

	public boolean isOrderEnable() {
		return orderEnable;
	}

	public void setOrderEnable(boolean orderEnable) {
		this.orderEnable = orderEnable;
	}

	public boolean hasFilter(String property) {
		return getFilter(property)!=null;
	}

	public Filter getFilter(String property) {
		return getFilter(getFilters(), property);
	}
	
	public static Filter getFilter(List<Filter> filters, String property) {
		if(filters==null || filters.size()==0 || property==null) {
			return null;
		}
		for(Filter f : filters) {
			if(f!=null && property.equals(f.getProperty())) {
				return f;
			}
		}
		return null;
	}

	public boolean hasOrder(String property) {
		return getOrder(property)!=null;
	}

	public Order getOrder(String property) {
		return getOrder(getOrders(), property);
	}
	
	public static Order getOrder(List<Order> orders, String property) {
		if(orders==null || orders.size()==0 || property==null) {
			return null;
		}
		for(Order o : orders) {
			if(o!=null && property.equals(o.getProperty())) {
				return o;
			}
		}
		
		return null;
	}

	public boolean hasSearchProperty(String property) {
		if(this.getSearchProperty()!=null && this.getSearchProperty().equals(property) && this.getSearchValue()!=null && !"".equals(this.getSearchValue())) {
			return true;
		}
		return false;
	}
	
	public boolean hasSearchOrder(String order) {
		if(this.getOrderProperty()!=null && this.getOrderProperty().equals(order)) {
			return true;
		}
		return false;
	}
	
	public Pageable addFilter(String property, Object value, Filter.Operator operator) {
		if(value==null || "".equals(value)) {
			return this;
		}
		if(operator==Filter.Operator.IN) {
			if(value.getClass().isArray() && ((Object[])value).length<=0) {
				return this;
			}else if(value instanceof Collection && ((Collection)value).isEmpty()) {
				return this;
			}
		}
		if(getFilters()==null) {
			this.setFilters(new ArrayList(8));
		}
		operator = operator==null?Filter.Operator.EQ:operator;
		this.getFilters().add(new Filter(property, operator, value));
		return this;
	}

	public Pageable addFilter(String property, Object value) {
		return this.addFilter(property, value, Filter.Operator.EQ);
	}

	public Pageable addFilterIn(String property, Object value) {
		return this.addFilter(property, value, Filter.Operator.IN);
	}

	public Pageable addFilterNotIn(String property, Object value) {
		return this.addFilter(property, value, Filter.Operator.NOT_IN);
	}
	
	public Pageable addFilterLike(String property, Object value) {
		if(value==null || "".equals(value)) {
			return this;
		}
		return this.addFilter(property, "%"+value+"%", Filter.Operator.LIKE);
	}
	
	public Pageable addFilterLikeRight(String property, Object value) {
		if(value==null || "".equals(value)) {
			return this;
		}
		return this.addFilter(property, value+"%", Filter.Operator.LIKE);
	}
	/**
	 * 大于等于
	 * @param property
	 * @param value
	 * @return
	 */
	public Pageable addFilterGE(String property, Object value) {
		return this.addFilter(property, value, Filter.Operator.GE);
	}
	/**
	 * 大于等于
	 * @param property
	 * @param value
	 * @return
	 */
	public Pageable addFilterGT(String property, Object value) {
		return this.addFilter(property, value, Filter.Operator.GT);
	}

	/**
	 * 小于等于
	 * @param property
	 * @param value
	 * @return
	 */
	public Pageable addFilterLE(String property, Object value) {
		return this.addFilter(property, value, Filter.Operator.LE);
	}

	/**
	 * 小于
	 * @param property
	 * @param value
	 * @return
	 */
	public Pageable addFilterLT(String property, Object value) {
		return this.addFilter(property, value, Filter.Operator.LT);
	}

	public Pageable addFilter(String property, Filter.Operator operator) {
		return this.addFilter(property, null, operator);
	}

	public Pageable addFilterIsNull(String property) {
		return this.addFilter(property, null, Filter.Operator.IS_NULL);
	}
	
	public Pageable addFilterIsNotNull(String property) {
		return this.addFilter(property, null, Filter.Operator.IS_NOT_NULL);
	}
	
	public Pageable addBetween(String property, Date date) {
		if(date==null) {
			return this;
		}
		addFilter(property, date, Filter.Operator.GE);
		addFilter(property, new Date(date.getTime()+86400000L), Filter.Operator.LT);
		return this;
	}
	
	public Pageable asc(String property) {
		if(property==null || "".equals(property)) {
			return this;
		}
		if(getOrders()==null) {
			setOrders(new ArrayList(6));
		}
		getOrders().add(Order.asc(property));
		
		return this;
	}
	
	public Pageable asc(int index, String property) {
		if(property==null || "".equals(property)) {
			return this;
		}
		if(getOrders()==null) {
			setOrders(new ArrayList(6));
		}
		getOrders().add(index, Order.asc(property));
		
		return this;
	}
	
	public Pageable desc(String property) {
		if(property==null || "".equals(property)) {
			return this;
		}
		if(getOrders()==null) {
			setOrders(new ArrayList(6));
		}
		getOrders().add(Order.desc(property));
		
		return this;
	}
	
	public Pageable desc(int index, String property) {
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