package com.xt.data.news.base;

import java.io.Serializable;
import java.util.List;

/**
 * Service - 基类
 * @author xt team
 * @version 0.0.1
 */
public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	T find(ID id);
	
	/**
	 * 查找实体对象
	 * @param id
	 * @param lazy
	 * @return
	 */
	T find(ID id, LazyInitialization<T> lazy);
	
	/**
	 * 查找实体对象
	 * @param id
	 * @param lazy
	 * @return
	 */
	T get(ID id, LazyInitialization<T> lazy);
	
	/**
	 * 查找实体对象
	 * @param id
	 * @return
	 */
	T get(ID id);
	
	/**
	 * 查找实体对象
	 * @param attr
	 * @param value
	 * @return
	 */
	T find(String attr, Object value);

	
	/**
	 * 查找实体对象
	 * @param attr
	 * @param value
	 * @param lazy
	 * @return
	 */
	T find(String attr, Object value, LazyInitialization<T> lazy);

	/**
	 * 查找所有实体对象集合
	 * 
	 * @return 所有实体对象集合
	 */
	List<T> findAll();

	/**
	 * 查找实体对象集合
	 * 
	 * @param ids
	 *            ID
	 * @return 实体对象集合
	 */
	@SuppressWarnings("unchecked")
	List<T> findList(ID... ids);
	
	/**
	 * 查找实体对象集合
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List<T> findList(LazyInitialization<T> lazy, ID... ids);


	/**
	 * 查找实体对象集合
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 实体对象集合
	 */
	@SuppressWarnings("unchecked")
	T find(List<Filter> filters, List<Order> orders);

	/**
	 * 查找实体对象集合
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 实体对象集合
	 */
	List<T> findList(Integer count, List<Filter> filters, List<Order> orders);
	
	/**
	 * 查找实体对象集合
	 * @param count
	 * @param filters
	 * @param orders
	 * @param lazy
	 * @return
	 */
	List<T> findList(Integer count, List<Filter> filters, List<Order> orders, LazyInitialization<T> lazy);

	/**
	 * 查找实体对象集合
	 * 
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 实体对象集合
	 */
	List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<T> findPage(Pageable pageable);

	/**
	 * 查询实体对象总数
	 * 
	 * @return 实体对象总数
	 */
	long count();

	/**
	 * 查询实体对象数量
	 * 
	 * @param filters
	 *            筛选
	 * @return 实体对象数量
	 */
	long count(Filter... filters);

	/**
	 * 判断实体对象是否存在
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象是否存在
	 */
	boolean exists(ID id);

	/**
	 * 判断实体对象是否存在
	 * 
	 * @param filters
	 *            筛选
	 * @return 实体对象是否存在
	 */
	boolean exists(Filter... filters);

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	T save(T entity);
	
	/**
	 * 保存实体对象
	 * @param entitys
	 * @return
	 */
	List<T> save(List<T> entitys);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	T update(T entity);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param ignoreProperties
	 *            忽略属性
	 * @return 实体对象
	 */
	T update(T entity, String... ignoreProperties);
	
	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param properties
	 *            忽略属性
	 * @return 实体对象
	 */
	T updateFor(T entity, String... properties);

	/**
	 * 删除实体对象
	 * 
	 * @param id
	 *            ID
	 */
	void delete(ID id);

	/**
	 * 删除实体对象
	 * 
	 * @param ids
	 *            ID
	 */
	@SuppressWarnings("unchecked")
	void delete(ID... ids);

	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void delete(T entity);

	/**
	 * QL语句更新
	 * @param t
	 * @param ids 根据字段更新
	 * @param isEmpty 是否判断空字符串
	 * @param passFields 跳过属性
	 * @return
	 */
	int updateField(T t, String[] ids, boolean isEmpty, String... passFields);

	/**
	 * QL语句更新
	 * @param t
	 * @param passFields 跳过属性
	 * @return
	 */
	int updateField(T t, String... passFields);

	/**
	 * 根据QL语句更新部分属性
	 * @param t
	 * @param updateFields 更新属性
	 * @return
	 */
	int updateFieldById(T t, String... updateFields);

	/**
	 * 设置为游离状态
	 * 
	 * @param entity
	 *            实体对象
	 */
	void detach(T entity);
	
	/**
	 * 刷新id生成
	 */
	void refreshIdGenerator();
}