package com.xt.data.news.service.impl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xt.data.news.base.BaseDao;
import com.xt.data.news.base.BaseEntity;
import com.xt.data.news.base.BaseService;
import com.xt.data.news.base.Filter;
import com.xt.data.news.base.FilterUtil;
import com.xt.data.news.base.LazyInitialization;
import com.xt.data.news.base.Order;
import com.xt.data.news.base.Page;
import com.xt.data.news.base.Pageable;

/**
 * Service - 基类
 * @author xt team
 * @version 0.0.1
 */
@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements BaseService<T, ID> {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);

	/**
	 * 更新忽略属性
	 */
	private static final String[] UPDATE_IGNORE_PROPERTIES = new String[] { BaseEntity.CREATED_DATE_PROPERTY_NAME, BaseEntity.LAST_MODIFIED_DATE_PROPERTY_NAME, BaseEntity.VERSION_PROPERTY_NAME };

	/**
	 * BaseDao
	 */
	private BaseDao<T, ID> baseDao;

	@Inject
	protected void setBaseDao(BaseDao<T, ID> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	@Transactional(readOnly = true)
	public T find(ID id) {
		return baseDao.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	public T find(ID id, LazyInitialization<T> lazy) {
		T entity = find(id);
		if(lazy!=null && entity!=null) {
			lazy.lazy(entity);
		}
		return entity;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public T get(ID id) {
		return baseDao.find(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public T get(ID id, LazyInitialization<T> lazy) {
		T entity = get(id);
		if(lazy!=null && entity!=null) {
			lazy.lazy(entity);
		}
		return entity;
	}
	
	@Override
	@Transactional(readOnly = true)
	public T find(List<Filter> filters, List<Order> orders) {
		List<T> data = findList(1, filters, orders);
		return data.isEmpty()?null:data.get(0);
	}
	
	@Override
	@Transactional(readOnly = true)
	public T find(String attr, Object value) {
		if(attr==null || value==null || "".equals(value)) {
			return null;
		}
		List<T> data = findList(1, FilterUtil.create().eq(attr, value).filters(), null);
		return data.isEmpty()?null:data.get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public T find(String attr, Object value, LazyInitialization<T> lazy) {
		T entity = find(attr, value);
		if(lazy!=null && entity!=null) {
			lazy.lazy(entity);
		}
		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		Integer first = null;
		Integer count = null;
		return findList(first, count, null, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<T> findList(ID... ids) {
		List<T> result = new ArrayList<>();
		if (ids != null) {
			for (ID id : ids) {
				T entity = find(id);
				if (entity != null) {
					result.add(entity);
				}
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<T> findList(LazyInitialization<T> lazy, ID... ids) {
		List<T> data = findList(ids);
		if(lazy!=null && data!=null && !data.isEmpty()) {
			for(T item : data) {
				lazy.lazy(item);
			}
		}
		return data;
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(Integer count, List<Filter> filters, List<Order> orders) {
		return findList(null, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(Integer count, List<Filter> filters, List<Order> orders, LazyInitialization<T> lazy) {
		List<T> data = findList(null, count, filters, orders);
		if(lazy!=null && data!=null && !data.isEmpty()) {
			for(T item : data) {
				lazy.lazy(item);
			}
		}
		return data;
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		return baseDao.findList(first, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findPage(Pageable pageable) {
		return baseDao.findPage(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public long count() {
		return count(new Filter[] {});
	}

	@Override
	@Transactional(readOnly = true)
	public long count(Filter... filters) {
		return baseDao.count(filters);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exists(ID id) {
		return baseDao.find(id) != null;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exists(Filter... filters) {
		return baseDao.count(filters) > 0;
	}

	@Override
	@Transactional
	public T save(T entity) {
		Assert.notNull(entity, "[Assertion failed] - entity is required; it must not be null");
		Assert.isTrue(entity.isNew(), "[Assertion failed] - entity must be new");

		baseDao.persist(entity);
		return entity;
	}
	
	@Override
	@Transactional
	public List<T> save(List<T> entitys) {
		Assert.notNull(entitys, "[Assertion failed] - entity is required; it must not be null");

		baseDao.persist(entitys);
		return entitys;
	}

	@Override
	@Transactional
	public T update(T entity) {
		Assert.notNull(entity, "[Assertion failed] - entity is required; it must not be null");
		Assert.isTrue(!entity.isNew(), "[Assertion failed] - entity must be new");

		if (!baseDao.isManaged(entity)) {
			T persistant = baseDao.find(baseDao.getIdentifier(entity));
			if (persistant != null) {
				copyProperties(entity, persistant, UPDATE_IGNORE_PROPERTIES);
			}
			return persistant;
		}
		return entity;
	}

	@Override
	@Transactional
	public T update(T entity, String... ignoreProperties) {
		Assert.notNull(entity, "[Assertion failed] - entity is required; it must not be null");
		Assert.isTrue(!entity.isNew(), "[Assertion failed] - entity must be new");
		Assert.isTrue(!baseDao.isManaged(entity), "[Assertion failed] - entity must not be managed");

		T persistant = baseDao.find(baseDao.getIdentifier(entity));
		if (persistant != null) {
			copyProperties(entity, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, UPDATE_IGNORE_PROPERTIES));
		}
		return update(persistant);
	}
	
	@Override
	@Transactional
	public T updateFor(T entity, String... properties) {
		Assert.notNull(entity, "[Assertion failed] - entity is required; it must not be null");
		Assert.notEmpty(properties, "[Assertion failed] - properties is empty; it must not be empty");
		Assert.isTrue(!entity.isNew(), "[Assertion failed] - entity must be new");
		Assert.isTrue(!baseDao.isManaged(entity), "[Assertion failed] - entity must not be managed");

		T persistant = baseDao.find(baseDao.getIdentifier(entity));
		if (persistant != null) {
			copyForProperties(entity, persistant, (String[]) ArrayUtils.addAll(properties, null));
		}
		return update(persistant);
	}

	@Override
	@Transactional
	public void delete(ID id) {
		delete(baseDao.find(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void delete(ID... ids) {
		if (ids != null) {
			for (ID id : ids) {
				delete(baseDao.find(id));
			}
		}
	}

	@Override
	@Transactional
	public void delete(T entity) {
		if (entity != null) {
			baseDao.remove(baseDao.isManaged(entity) ? entity : baseDao.merge(entity));
		}
	}

	/**
	 * 拷贝对象属性
	 * 
	 * @param source
	 *            源
	 * @param target
	 *            目标
	 * @param ignoreProperties
	 *            忽略属性
	 */
	protected void copyProperties(T source, T target, String... ignoreProperties) {
		Assert.notNull(source, "[Assertion failed] - source is required; it must not be null");
		Assert.notNull(target, "[Assertion failed] - target is required; it must not be null");

		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			Method readMethod = propertyDescriptor.getReadMethod();
			Method writeMethod = propertyDescriptor.getWriteMethod();
			if (ArrayUtils.contains(ignoreProperties, propertyName) || readMethod == null || writeMethod == null || !baseDao.isLoaded(source, propertyName)) {
				continue;
			}
			try {
				Object sourceValue = readMethod.invoke(source);
				writeMethod.invoke(target, sourceValue);
			} catch (IllegalAccessException e) {
				LOGGER.error("source class:{}, target class:{}, propertyDescriptor:{}", source.getClass(), target.getClass(), propertyDescriptor.getName());
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				LOGGER.error("source class:{}, target class:{}, propertyDescriptor:{}", source.getClass(), target.getClass(), propertyDescriptor.getName());
				throw new RuntimeException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				LOGGER.error("source class:{}, target class:{}, propertyDescriptor:{}", source.getClass(), target.getClass(), propertyDescriptor.getName());
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 拷贝对象属性
	 * 
	 * @param source
	 *            源
	 * @param target
	 *            目标
	 * @param properties 属性
	 */
	protected void copyForProperties(T source, T target, String... properties) {
		Assert.notNull(source, "[Assertion failed] - source is required; it must not be null");
		Assert.notNull(target, "[Assertion failed] - target is required; it must not be null");

		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			Method readMethod = propertyDescriptor.getReadMethod();
			Method writeMethod = propertyDescriptor.getWriteMethod();
			if (ArrayUtils.contains(properties, propertyName)) {
				try {
					Object sourceValue = readMethod.invoke(source);
					writeMethod.invoke(target, sourceValue);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 *  拷贝对象属性
	 * @param source
	 * @param target
	 * @param model (0空属性不拷贝，1空属性空字符串不拷贝)
	 * @param ignoreProperties
	 */
	protected void copyProperties(T source, T target, int model, String... ignoreProperties) {
		Assert.notNull(source, "[Assertion failed] - source is required; it must not be null");
		Assert.notNull(target, "[Assertion failed] - target is required; it must not be null");

		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			Method readMethod = propertyDescriptor.getReadMethod();
			Method writeMethod = propertyDescriptor.getWriteMethod();
			if (ArrayUtils.contains(ignoreProperties, propertyName) || readMethod == null || writeMethod == null || !baseDao.isLoaded(source, propertyName)) {
				continue;
			}
			try {
				Object sourceValue = readMethod.invoke(source);
				if(sourceValue!=null) {
					if(model==0) {
						writeMethod.invoke(target, sourceValue);
					}else if(model==1 && !"".equals(sourceValue)) {
						writeMethod.invoke(target, sourceValue);
					}
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	@Override
	@Transactional
	public int updateField(T t, String[] ids, boolean isEmpty, String... passFields) {
		return baseDao.update(t, ids, isEmpty, passFields);
	}

	@Override
	@Transactional
	public int updateField(T t, String... passFields) {
		return baseDao.update(t, passFields);
	}

	@Override
	@Transactional
	public int updateFieldById(T t, String... updateFields) {
		return baseDao.updateById(t, updateFields);
	}
	
	@Override
	public void detach(T entity) {
		baseDao.detach(entity);
	}
	
	@Override
	public void refreshIdGenerator() {
		baseDao.refreshIdGenerator();
		baseDao.flush();
	}
}