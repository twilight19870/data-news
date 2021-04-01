package com.xt.data.news.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.LockModeType;

/**
 * Dao - 基类
 * @author xt team
 * @version 0.0.1
 */
public interface BaseDao<T extends BaseEntity<ID>, ID extends Serializable> {

	/**
	 * 判断是否存在
	 * 
	 * @param attributeName
	 *            属性名称
	 * @param attributeValue
	 *            属性值
	 * @return 是否存在
	 */
	boolean exists(String attributeName, Object attributeValue);

	/**
	 * 判断是否存在
	 * 
	 * @param attributeName
	 *            属性名称
	 * @param attributeValue
	 *            属性值
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 是否存在
	 */
	boolean exists(String attributeName, String attributeValue, boolean ignoreCase);

	/**
	 * 判断是否唯一
	 * 
	 * @param id
	 *            ID
	 * @param attributeName
	 *            属性名称
	 * @param attributeValue
	 *            属性值
	 * @return 是否唯一
	 */
	boolean unique(ID id, String attributeName, Object attributeValue);

	/**
	 * 判断是否唯一
	 * 
	 * @param id
	 *            ID
	 * @param attributeName
	 *            属性名称
	 * @param attributeValue
	 *            属性值
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 是否唯一
	 */
	boolean unique(ID id, String attributeName, String attributeValue, boolean ignoreCase);

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
	 * 
	 * @param id
	 *            ID
	 * @param lockModeType
	 *            锁定方式
	 * @return 实体对象，若不存在则返回null
	 */
	T find(ID id, LockModeType lockModeType);

	/**
	 * 查找实体对象
	 * 
	 * @param attributeName
	 *            属性名称
	 * @param attributeValue
	 *            属性值
	 * @return 实体对象，若不存在则返回null
	 */
	T find(String attributeName, Object attributeValue);

	/**
	 * 查找实体对象
	 * 
	 * @param attributeName
	 *            属性名称
	 * @param attributeValue
	 *            属性值
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 实体对象，若不存在则返回null
	 */
	T find(String attributeName, String attributeValue, boolean ignoreCase);

	/**
	 * 查找实体对象
	 * 
	 * @param attributeName
	 *            属性名称
	 * @param attributeValue
	 *            属性值
	 * @param lockModeType
	 *            锁定方式
	 * @return 实体对象，若不存在则返回null
	 */
	T find(String attributeName, Object attributeValue, LockModeType lockModeType);

	/**
	 * 查找实体对象
	 * 
	 * @param attributeName
	 *            属性名称
	 * @param attributeValue
	 *            属性值
	 * @param ignoreCase
	 *            忽略大小写
	 * @param lockModeType
	 *            锁定方式
	 * @return 实体对象，若不存在则返回null
	 */
	T find(String attributeName, String attributeValue, boolean ignoreCase, LockModeType lockModeType);

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
	 * 查询实体对象数量
	 * 
	 * @param filters
	 *            筛选
	 * @return 实体对象数量
	 */
	long count(Filter... filters);

	/**
	 * 持久化实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void persist(T entity);

	/**
	 * 持久化实体对象
	 * @param entitys
	 */
	void persist(List<T> entitys);

	/**
	 * 合并实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	T merge(T entity);

	/**
	 * 移除实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void remove(T entity);

	/**
	 * 刷新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void refresh(T entity);

	/**
	 * 刷新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param lockModeType
	 *            锁定方式
	 */
	void refresh(T entity, LockModeType lockModeType);

	/**
	 * 获取实体对象ID
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象ID
	 */
	ID getIdentifier(T entity);

	/**
	 * 判断实体对象是否已加载
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象是否已加载
	 */
	boolean isLoaded(T entity);

	/**
	 * 判断实体对象属性是否已加载
	 * 
	 * @param entity
	 *            实体对象
	 * @param attributeName
	 *            属性名称
	 * @return 实体对象属性是否已加载
	 */
	boolean isLoaded(T entity, String attributeName);

	/**
	 * 判断是否为托管状态
	 * 
	 * @param entity
	 *            实体对象
	 * @return 是否为托管状态
	 */
	boolean isManaged(T entity);

	/**
	 * 设置为游离状态
	 * 
	 * @param entity
	 *            实体对象
	 */
	void detach(T entity);

	/**
	 * 获取锁定方式
	 * 
	 * @param entity
	 *            实体对象
	 * @return 锁定方式
	 */
	LockModeType getLockMode(T entity);

	/**
	 * 锁定实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param lockModeType
	 *            锁定方式
	 */
	void lock(T entity, LockModeType lockModeType);

	/**
	 * 清除缓存
	 */
	void clear();

	/**
	 * 同步数据
	 */
	void flush();

	/**
	 * QL语句更新
	 * @param t
	 * @param ids 根据字段更新
	 * @param isEmpty 是否判断空字符串
	 * @param passFields 跳过属性
	 * @return
	 */
	int update(T t, String[] ids, boolean isEmpty, String... passFields);

	/**
	 * QL语句更新
	 * @param t
	 * @param passFields 跳过属性
	 * @return
	 */
	int update(T t, String... passFields);

	/**
	 * 根据QL语句更新部分属性
	 * @param t
	 * @param updateFields 更新属性
	 * @return
	 */
	int updateById(T t, String... updateFields);

	/**
	 * 刷新id生成器
	 */
	int refreshIdGenerator();
	
	/**
	 * 获取sqlDao(sqltoy)
	 * @return
	
	public SqltoyDao<T> getSqlDao(); */
}