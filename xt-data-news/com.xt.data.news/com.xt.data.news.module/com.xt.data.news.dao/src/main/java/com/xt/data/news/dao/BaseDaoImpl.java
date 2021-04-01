package com.xt.data.news.dao;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonView;
import com.xt.data.news.base.BaseDao;
import com.xt.data.news.base.BaseEntity;
import com.xt.data.news.base.Filter;
import com.xt.data.news.base.Order;
import com.xt.data.news.base.OrderedEntity;
import com.xt.data.news.base.Page;
import com.xt.data.news.base.Pageable;
import com.xt.data.news.base.Filter.Operator;

import cn.hutool.core.bean.BeanUtil;

/**
 * Dao - 基类
 * @author xt team
 * @version 0.0.1
 */
public abstract class BaseDaoImpl<T extends BaseEntity<ID>, ID extends Serializable> implements BaseDao<T, ID> {

	private final static Logger LOGGER = LoggerFactory.getLogger(BaseDaoImpl.class);

	public static String[] DEFAULT_PASS_FIELDS = new String[] {BaseEntity.ID_PROPERTY_NAME, BaseEntity.CREATED_DATE_PROPERTY_NAME, BaseEntity.VERSION_PROPERTY_NAME};

	/**
	 * Field缓存
	 */
	private static final Map<Class<?>, Field[]> DECLARED_FIELDS_CACHE = new ConcurrentHashMap<>();
	
	/**
	 * 左查询属性
	 */
	private static final Set<String> LEFT_SRARCH_PROPERTYS = new HashSet() {{
		add("sn");
		add("username");
		add("mobile");
		add("phone");
	}};

	/**
	 * 属性分隔符
	 */
	private static final String ATTRIBUTE_SEPARATOR = ".";

	/**
	 * 别名前缀
	 */
	private static final String ALIAS_PREFIX = "xtGeneratedAlias";

	/**
	 * 别名数
	 */
	private static volatile long aliasCount = 0L;

	/**
	 * 实体类类型
	 */
	private Class<T> entityClass;

	@PersistenceContext
	protected EntityManager entityManager;

	/**
	 * 构造方法
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ResolvableType resolvableType = ResolvableType.forClass(getClass());
		entityClass = (Class<T>) resolvableType.as(BaseDaoImpl.class).getGeneric().resolve();
	}

	@Override
	public boolean exists(String attributeName, Object attributeValue) {
		Assert.hasText(attributeName, "[Assertion failed] - attributeName must have text; it must not be null, empty, or blank");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(criteriaBuilder.count(root));
		criteriaQuery.where(criteriaBuilder.equal(root.get(attributeName), attributeValue));
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult() > 0;
	}

	@Override
	public boolean exists(String attributeName, String attributeValue, boolean ignoreCase) {
		Assert.hasText(attributeName, "[Assertion failed] - attributeName must have text; it must not be null, empty, or blank");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(criteriaBuilder.count(root));
		if (ignoreCase) {
			criteriaQuery.where(criteriaBuilder.equal(criteriaBuilder.lower(root.<String>get(attributeName)), StringUtils.lowerCase(attributeValue)));
		} else {
			criteriaQuery.where(criteriaBuilder.equal(root.get(attributeName), attributeValue));
		}
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult() > 0;
	}

	@Override
	public boolean unique(ID id, String attributeName, Object attributeValue) {
		Assert.hasText(attributeName, "[Assertion failed] - attributeName must have text; it must not be null, empty, or blank");

		if (id != null) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<T> root = criteriaQuery.from(entityClass);
			criteriaQuery.select(criteriaBuilder.count(root));
			criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(attributeName), attributeValue), criteriaBuilder.notEqual(root.get(BaseEntity.ID_PROPERTY_NAME), id)));
			TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
			return query.getSingleResult() <= 0;
		} else {
			return !exists(attributeName, attributeValue);
		}
	}

	@Override
	public boolean unique(ID id, String attributeName, String attributeValue, boolean ignoreCase) {
		Assert.hasText(attributeName, "[Assertion failed] - attributeName must have text; it must not be null, empty, or blank");

		if (id != null) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<T> root = criteriaQuery.from(entityClass);
			criteriaQuery.select(criteriaBuilder.count(root));
			if (ignoreCase) {
				criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.lower(root.<String>get(attributeName)), StringUtils.lowerCase(attributeValue)), criteriaBuilder.notEqual(root.get(BaseEntity.ID_PROPERTY_NAME), id)));
			} else {
				criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(attributeName), attributeValue), criteriaBuilder.notEqual(root.get(BaseEntity.ID_PROPERTY_NAME), id)));
			}
			TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
			return query.getSingleResult() <= 0;
		} else {
			return !exists(attributeName, attributeValue);
		}
	}

	@Override
	public T find(ID id) {
		if (id == null) {
			return null;
		}
		return entityManager.find(entityClass, id);
	}

	@Override
	public T find(ID id, LockModeType lockModeType) {
		if (id == null) {
			return null;
		}
		if (lockModeType != null) {
			return entityManager.find(entityClass, id, lockModeType);
		} else {
			return entityManager.find(entityClass, id);
		}
	}

	@Override
	public T find(String attributeName, Object attributeValue) {
		Assert.hasText(attributeName, "[Assertion failed] - attributeName must have text; it must not be null, empty, or blank");

		return find(attributeName, attributeValue, null);
	}

	@Override
	public T find(String attributeName, String attributeValue, boolean ignoreCase) {
		Assert.hasText(attributeName, "[Assertion failed] - attributeName must have text; it must not be null, empty, or blank");

		return find(attributeName, attributeValue, ignoreCase, null);
	}

	@Override
	public T find(String attributeName, Object attributeValue, LockModeType lockModeType) {
		Assert.hasText(attributeName, "[Assertion failed] - attributeName must have text; it must not be null, empty, or blank");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get(attributeName), attributeValue));
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
		if (lockModeType != null) {
			query.setLockMode(lockModeType);
		}
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public T find(String attributeName, String attributeValue, boolean ignoreCase, LockModeType lockModeType) {
		Assert.hasText(attributeName, "[Assertion failed] - attributeName must have text; it must not be null, empty, or blank");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		criteriaQuery.where();
		if (ignoreCase) {
			criteriaQuery.where(criteriaBuilder.equal(criteriaBuilder.lower(root.<String>get(attributeName)), StringUtils.lowerCase(attributeValue)));
		} else {
			criteriaQuery.where(criteriaBuilder.equal(root.get(attributeName), attributeValue));
		}
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
		if (lockModeType != null) {
			query.setLockMode(lockModeType);
		}
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return findList(criteriaQuery, first, count, filters, orders);
	}

	@Override
	public Page<T> findPage(Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return findPage(criteriaQuery, pageable);
	}

	@Override
	public long count(Filter... filters) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return count(criteriaQuery, ArrayUtils.isNotEmpty(filters) ? Arrays.asList(filters) : null);
	}

	@Override
	public void persist(T entity) {
		Assert.notNull(entity, "[Assertion failed] - entity is required; it must not be null");

		entityManager.persist(entity);
	}

	@Override
	public void persist(List<T> entitys) {
		Assert.notNull(entitys, "[Assertion failed] - entity is required; it must not be null");
		int i = 0;
		for (T entity : entitys) {
			persist(entity);
			i++;
			if (i % 100 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

	@Override
	public T merge(T entity) {
		Assert.notNull(entity, "[Assertion failed] - entity is required; it must not be null");

		return entityManager.merge(entity);
	}

	@Override
	public void remove(T entity) {
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	@Override
	public void refresh(T entity) {
		if (entity != null) {
			entityManager.refresh(entity);
		}
	}

	@Override
	public void refresh(T entity, LockModeType lockModeType) {
		if (entity != null) {
			if (lockModeType != null) {
				entityManager.refresh(entity, lockModeType);
			} else {
				entityManager.refresh(entity);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ID getIdentifier(T entity) {
		Assert.notNull(entity, "[Assertion failed] - entity is required; it must not be null");

		return (ID) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
	}

	@Override
	public boolean isLoaded(T entity) {
		Assert.notNull(entity, "[Assertion failed] - entity is required; it must not be null");

		return entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(entity);
	}

	@Override
	public boolean isLoaded(T entity, String attributeName) {
		Assert.notNull(entity, "[Assertion failed] - entity is required; it must not be null");
		Assert.hasText(attributeName, "[Assertion failed] - attributeName must have text; it must not be null, empty, or blank");

		return entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(entity, attributeName);
	}

	@Override
	public boolean isManaged(T entity) {
		Assert.notNull(entity, "[Assertion failed] - entity is required; it must not be null");

		return entityManager.contains(entity);
	}

	@Override
	public void detach(T entity) {
		if (entity != null) {
			entityManager.detach(entity);
		}
	}

	@Override
	public LockModeType getLockMode(T entity) {
		Assert.notNull(entity, "[Assertion failed] - entity is required; it must not be null");

		return entityManager.getLockMode(entity);
	}

	@Override
	public void lock(T entity, LockModeType lockModeType) {
		if (entity != null && lockModeType != null) {
			entityManager.lock(entity, lockModeType);
		}
	}

	@Override
	public void clear() {
		entityManager.clear();
	}

	@Override
	public void flush() {
		entityManager.flush();
	}
	
	private Object getInvoie(Method get, Object t) {
		try {
			return get.invoke(t);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public int updateById(T t, String... updateFields) {
		if(t==null || t.getId()==null || updateFields==null || updateFields.length==0) {
			return 0;
		}

		String m = "";
		Map<String, Object> params = new HashMap();
		StringBuffer qlString = new StringBuffer("update ").append(t.getClass()).append(" t set ");
		Map<String,PropertyDescriptor> propertyMap = BeanUtil.getPropertyDescriptorMap(t.getClass(), true);
		for(String field : updateFields) {
			PropertyDescriptor property = propertyMap.get(field);
			if(property==null) {
				throw new RuntimeException(field+"不存在");
			}
			Method get = property.getReadMethod();
			if(get==null) {
				throw new RuntimeException(field+"不可读取");
			}
			Object value = getInvoie(get, t);
			params.put(field, value);
			qlString.append(m).append(" t.").append(field).append(" = :").append(field).append(" ");
			m = " and ";
		}

		qlString.append(" where t.id = :id ");
		params.put("id", t.getId());

		//执行更新语句
		Query qry = entityManager.createQuery(qlString.toString());
		LOGGER.debug("execute update ql sql:{}", qlString);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			LOGGER.debug("set parameter {} = {}", entry.getKey(), entry.getValue());
			qry.setParameter(entry.getKey(), entry.getValue());
		}

		return qry.executeUpdate();
	}


	@Override
	public int update(T t, String... passFields) {
		if(passFields==null||passFields.length==0) {
			return update(t, null, true, DEFAULT_PASS_FIELDS);
		}else {
			int len = passFields.length;
			passFields = Arrays.copyOf(passFields, len+DEFAULT_PASS_FIELDS.length);
			System.arraycopy(DEFAULT_PASS_FIELDS, 0, passFields, len, DEFAULT_PASS_FIELDS.length);
			return update(t, null, true, passFields);
		}
	}

	@Override
	public int update(T t, String[] ids, boolean isEmpty, String... passFields) {
		if(t==null) {
			return 0;
		}
		if(ids==null) {
			//默认使用id
			ids = new String[]{"id"};
		}

		//拼接where条件
		String m = "";
		Set<String> pass = new HashSet();
		Map<String, Object> params = new HashMap();
		StringBuffer where = new StringBuffer(" where ");
		Map<String,PropertyDescriptor> propertyMap = BeanUtil.getPropertyDescriptorMap(t.getClass(), true);
		for(String id : ids) {
			PropertyDescriptor property = propertyMap.get(id);
			if(property==null) {
				throw new RuntimeException(id+"不存在");
			}
			Method get = property.getReadMethod();
			if(get==null) {
				throw new RuntimeException(id+"不可读取");
			}
			
			Object value = getInvoie(get, t);
			if(value==null || (isEmpty && "".equals(value))) {
				throw new RuntimeException(id+"为空");
			}

			params.put(id, value);
			where.append(m).append(" t.").append(id).append(" = :").append(id).append(" ");
			m = " and ";
			pass.add(id);
		}
		if(passFields!=null) {
			for(String field : passFields) {
				pass.add(field);
			}
		}

		//拼接set语句
		m = "";
		int updates = 0;
		StringBuffer qlString = new StringBuffer("update ").append(t.getClass()).append(" t set ");
		for(PropertyDescriptor property : propertyMap.values()) {
			String name = property.getName();
			if(pass.contains(name)) {
				continue;
			}

			Method get = property.getReadMethod();
			if(get==null) {
				continue;
			}

			Object value = getInvoie(get, t);
			if(value==null || (isEmpty && "".equals(value))) {
				continue;
			}

			params.put(name, value);
			qlString.append(m).append(" t.").append(name).append(" = :").append(name).append(" ");
			m = ",";
			updates++;
		}
		if(updates==0) {
			return 0;
		}

		//设置参数、执行更新语句
		qlString.append(where);
		Query qry = entityManager.createQuery(qlString.toString());
		LOGGER.debug("execute update ql sql:{}", qlString);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			LOGGER.debug("set parameter {} = {}", entry.getKey(), entry.getValue());
			qry.setParameter(entry.getKey(), entry.getValue());
		}

		return qry.executeUpdate();
	}
	
	public int update(T entity, Class jsonViewClass) {
		List<Field> fields = findFields(entity.getClass(), JsonView.class);
		List<String> passFields = new ArrayList();
		for(Field field : fields) {
			JsonView jsonView = field.getAnnotation(JsonView.class);
			boolean isUpdate = false;
			if(jsonView.value()!=null) {
				for(Class item : jsonView.value()) {
					if(item == jsonViewClass) {
						isUpdate = true;
						break;
					}
				}
			}
			if(!isUpdate) {
				passFields.add(field.getName());
			}
		}
		return update(entity, passFields.toArray(new String[0]));
	}

	/**
	 * 刷新ID
	 */
	@Override
	public int refreshIdGenerator() {
		if(entityClass==null) {
			return 0;
		}
		String sql = "update IdGenerator ig\n" +
				"INNER JOIN (\n" +
				"	SELECT '"+entityClass.getSimpleName()+"' as 'name', ifnull(max(id), 1) curr_id from "+entityClass.getSimpleName()+"\n" +
				") t1 on t1.name = ig.sequence_name\n" +
				"set ig.next_val = if(ig.next_val > t1.curr_id, ig.next_val+1, t1.curr_id+1)";
		return entityManager.createNativeQuery(sql).executeUpdate();
	}

	/**
	 * 查找实体对象集合
	 *
	 * @param criteriaQuery
	 *            查询条件
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param lockModeType
	 *            锁定方式
	 * @return 实体对象集合
	 */
	protected List<T> findList(CriteriaQuery<T> criteriaQuery, Integer first, Integer count, List<Filter> filters, List<Order> orders, LockModeType lockModeType) {
		Assert.notNull(criteriaQuery, "[Assertion failed] - criteriaQuery is required; it must not be null");
		Assert.notNull(criteriaQuery.getSelection(), "[Assertion failed] - criteriaQuery selection is required; it must not be null");
		Assert.notEmpty(criteriaQuery.getRoots(), "[Assertion failed] - criteriaQuery roots must not be empty: it must contain at least 1 element");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Root<T> root = findRoot(criteriaQuery, criteriaQuery.getResultType());

		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, toPredicate(root, filters));
		criteriaQuery.where(restrictions);

		List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
		orderList.addAll(criteriaQuery.getOrderList());
		orderList.addAll(toOrders(root, orders));
		if (CollectionUtils.isEmpty(orderList)) {
			if (OrderedEntity.class.isAssignableFrom(entityClass)) {
				orderList.add(criteriaBuilder.asc(getPath(root, OrderedEntity.ORDER_PROPERTY_NAME)));
			} else {
				orderList.add(criteriaBuilder.desc(getPath(root, OrderedEntity.CREATED_DATE_PROPERTY_NAME)));
			}
		}
		criteriaQuery.orderBy(orderList);

		TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
		if (first != null) {
			query.setFirstResult(first);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		if (lockModeType != null) {
			query.setLockMode(lockModeType);
		}
		return query.getResultList();
	}

	/**
	 * 查找实体对象集合
	 *
	 * @param criteriaQuery
	 *            查询条件
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
	protected List<T> findList(CriteriaQuery<T> criteriaQuery, Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		return findList(criteriaQuery, first, count, filters, orders, null);
	}

	/**
	 * 查找实体对象集合
	 *
	 * @param criteriaQuery
	 *            查询条件
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @return 实体对象集合
	 */
	protected List<T> findList(CriteriaQuery<T> criteriaQuery, Integer first, Integer count) {
		return findList(criteriaQuery, first, count, null, null, null);
	}

	/**
	 * 查找实体对象集合
	 *
	 * @param criteriaQuery
	 *            查询条件
	 * @return 实体对象集合
	 */
	protected List<T> findList(CriteriaQuery<T> criteriaQuery) {
		return findList(criteriaQuery, null, null, null, null, null);
	}

	/**
	 * 查找实体对象分页
	 *
	 * @param criteriaQuery
	 *            查询条件
	 * @param pageable
	 *            分页信息
	 * @param lockModeType
	 *            锁定方式
	 * @return 实体对象分页
	 */
	protected Page<T> findPage(CriteriaQuery<T> criteriaQuery, Pageable pageable, LockModeType lockModeType) {
		Assert.notNull(criteriaQuery, "[Assertion failed] - criteriaQuery is required; it must not be null");
		Assert.notNull(criteriaQuery.getSelection(), "[Assertion failed] - criteriaQuery selection is required; it must not be null");
		Assert.notEmpty(criteriaQuery.getRoots(), "[Assertion failed] - criteriaQuery roots must not be empty: it must contain at least 1 element");

		if (pageable == null) {
			pageable = new Pageable();
		}

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Root<T> root = findRoot(criteriaQuery, criteriaQuery.getResultType());

		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, toPredicate(root, pageable.getFilters()));
		String searchProperty = pageable.getSearchProperty();
		String searchValue = pageable.getSearchValue();
		if (pageable.isSearchEnable() && StringUtils.isNotEmpty(searchProperty) && StringUtils.isNotEmpty(searchValue)) {
			Path<String> searchPath = getPath(root, searchProperty);
			if (searchPath != null) {
				Pageable.SearchModel searchModel = pageable.getSearchModel();
				if(searchModel==null && LEFT_SRARCH_PROPERTYS.contains(searchProperty)) {
					//部分字段，默认使用左查询
					searchModel = Pageable.SearchModel.LEFT;
				}
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(searchPath,  Pageable.SearchModel.val(searchModel, searchValue)));
			}
		}
		criteriaQuery.where(restrictions);

		List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
		orderList.addAll(criteriaQuery.getOrderList());
		orderList.addAll(toOrders(root, pageable.getOrders()));
		String orderProperty = pageable.getOrderProperty();
		Order.Direction orderDirection = pageable.getOrderDirection();
		if (pageable.isOrderEnable() && StringUtils.isNotEmpty(orderProperty) && orderDirection != null) {
			Path<?> orderPath = getPath(root, orderProperty);
			if (orderPath != null) {
				switch (orderDirection) {
					case ASC:
						orderList.add(criteriaBuilder.asc(orderPath));
						break;
					case DESC:
						orderList.add(criteriaBuilder.desc(orderPath));
						break;
				}
			}
		}
		if (CollectionUtils.isEmpty(orderList)) {
			if (OrderedEntity.class.isAssignableFrom(entityClass)) {
				orderList.add(criteriaBuilder.asc(getPath(root, OrderedEntity.ORDER_PROPERTY_NAME)));
			} else {
				Order.By orderBy = entityClass.getAnnotation(Order.By.class);
				if(orderBy!=null && orderBy.property()!=null && !"".equals(orderBy.property())) {
					orderList.add(orderBy.direction()==null||orderBy.direction()==Order.Direction.ASC? criteriaBuilder.asc(getPath(root, orderBy.property())) : criteriaBuilder.desc(getPath(root, orderBy.property())));
				}else {
					orderList.add(criteriaBuilder.desc(getPath(root, OrderedEntity.LAST_MODIFIED_DATE_PROPERTY_NAME)));
				}
			}
		}
		criteriaQuery.orderBy(orderList);

		long total = count(criteriaQuery, null);
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		if (lockModeType != null) {
			query.setLockMode(lockModeType);
		}
		return new Page<>(query.getResultList(), total, pageable);
	}

	/**
	 * 查找实体对象分页
	 *
	 * @param criteriaQuery
	 *            查询条件
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	protected Page<T> findPage(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		return findPage(criteriaQuery, pageable, null);
	}

	/**
	 * 查询实体对象数量
	 *
	 * @param criteriaQuery
	 *            查询条件
	 * @param filters
	 *            筛选
	 * @return 实体对象数量
	 */
	protected Long count(CriteriaQuery<T> criteriaQuery, List<Filter> filters) {
		Assert.notNull(criteriaQuery, "[Assertion failed] - criteriaQuery is required; it must not be null");
		Assert.notNull(criteriaQuery.getSelection(), "[Assertion failed] - criteriaQuery selection is required; it must not be null");
		Assert.notEmpty(criteriaQuery.getRoots(), "[Assertion failed] - criteriaQuery roots must not be empty: it must contain at least 1 element");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Root<T> root = findRoot(criteriaQuery, criteriaQuery.getResultType());

		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, toPredicate(root, filters));
		criteriaQuery.where(restrictions);

		CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
		copyCriteriaWithoutSelectionAndOrder(criteriaQuery, countCriteriaQuery);

		Root<?> countRoot = findRoot(countCriteriaQuery, criteriaQuery.getResultType());
		if (criteriaQuery.isDistinct()) {
			countCriteriaQuery.select(criteriaBuilder.countDistinct(countRoot));
		} else {
			countCriteriaQuery.select(criteriaBuilder.count(countRoot));
		}
		return entityManager.createQuery(countCriteriaQuery).getSingleResult();
	}

	/**
	 * 查询实体对象数量
	 *
	 * @param criteriaQuery
	 *            查询条件
	 * @return 实体对象数量
	 */
	protected Long count(CriteriaQuery<T> criteriaQuery) {
		return count(criteriaQuery, null);
	}

	/**
	 * 查找Root
	 *
	 * @param criteriaQuery
	 *            查询条件
	 * @param clazz
	 *            类型
	 * @return Root
	 */
	@SuppressWarnings("unchecked")
	private Root<T> findRoot(CriteriaQuery<?> criteriaQuery, Class<T> clazz) {
		Assert.notNull(criteriaQuery, "[Assertion failed] - criteriaQuery is required; it must not be null");
		Assert.notNull(clazz, "[Assertion failed] - clazz is required; it must not be null");

		for (Root<?> root : criteriaQuery.getRoots()) {
			if (clazz.equals(root.getJavaType())) {
				return (Root<T>) root.as(clazz);
			}
		}
		return (Root<T>) criteriaQuery.getRoots().iterator().next();
	}

	/**
	 * 获取Path
	 *
	 * @param path
	 *            Path
	 * @param attributeName
	 *            属性名称
	 * @return Path
	 */
	@SuppressWarnings("unchecked")
	private <X> Path<X> getPath(Path<?> path, String attributeName) {
		if (path == null || StringUtils.isEmpty(attributeName)) {
			return (Path<X>) path;
		}
		return getPath(path.get(StringUtils.substringBefore(attributeName, ATTRIBUTE_SEPARATOR)), StringUtils.substringAfter(attributeName, ATTRIBUTE_SEPARATOR));
	}

	/**
	 * 获取或创建别名
	 *
	 * @param selection
	 *            Selection
	 * @return 别名
	 */
	private synchronized String getOrCreateAlias(Selection<?> selection) {
		Assert.notNull(selection, "[Assertion failed] - selection is required; it must not be null");

		String alias = selection.getAlias();
		if (alias == null) {
			if (aliasCount > 1000) {
				aliasCount = 0;
			}
			alias = ALIAS_PREFIX + aliasCount++;
			selection.alias(alias);
		}
		return alias;

	}

	/**
	 * 拷贝Join
	 *
	 * @param from
	 *            源
	 * @param to
	 *            目标
	 */
	private void copyJoins(From<?, ?> from, From<?, ?> to) {
		Assert.notNull(from, "[Assertion failed] - from is required; it must not be null");
		Assert.notNull(to, "[Assertion failed] - to is required; it must not be null");

		for (Join<?, ?> fromJoin : from.getJoins()) {
			Join<?, ?> toJoin = to.join(fromJoin.getAttribute().getName(), fromJoin.getJoinType());
			toJoin.alias(getOrCreateAlias(fromJoin));
			copyJoins(fromJoin, toJoin);
		}
	}

	/**
	 * 拷贝Fetch
	 *
	 * @param from
	 *            源
	 * @param to
	 *            目标
	 */
	private void copyFetches(From<?, ?> from, From<?, ?> to) {
		Assert.notNull(from, "[Assertion failed] - from is required; it must not be null");
		Assert.notNull(to, "[Assertion failed] - to is required; it must not be null");

		for (Fetch<?, ?> fromFetch : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(fromFetch.getAttribute().getName());
			copyFetches(fromFetch, toFetch);
		}
	}

	/**
	 * 拷贝Fetch
	 *
	 * @param from
	 *            源
	 * @param to
	 *            目标
	 */
	private void copyFetches(Fetch<?, ?> from, Fetch<?, ?> to) {
		Assert.notNull(from, "[Assertion failed] - from is required; it must not be null");
		Assert.notNull(to, "[Assertion failed] - to is required; it must not be null");

		for (Fetch<?, ?> fromFetch : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(fromFetch.getAttribute().getName());
			copyFetches(fromFetch, toFetch);
		}
	}

	/**
	 * 拷贝查询条件(不包含Selection、Order)
	 *
	 * @param from
	 *            源
	 * @param to
	 *            目标
	 */
	private void copyCriteriaWithoutSelectionAndOrder(CriteriaQuery<?> from, CriteriaQuery<?> to) {
		Assert.notNull(from, "[Assertion failed] - from is required; it must not be null");
		Assert.notNull(to, "[Assertion failed] - to is required; it must not be null");

		for (Root<?> root : from.getRoots()) {
			Root<?> dest = to.from(root.getJavaType());
			dest.alias(getOrCreateAlias(root));
			copyJoins(root, dest);
			copyFetches(root, dest);
		}

		to.groupBy(from.getGroupList());
		to.distinct(from.isDistinct());

		if (from.getGroupRestriction() != null) {
			to.having(from.getGroupRestriction());
		}

		if (from.getRestriction() != null) {
			to.where(from.getRestriction());
		}
	}

	/**
	 * 转换为Predicate
	 *
	 * @param root
	 *            Root
	 * @param filters
	 *            筛选
	 * @return Predicate
	 */
	@SuppressWarnings("unchecked")
	private Predicate toPredicate(Root<T> root, List<Filter> filters) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaBuilder.conjunction();
		if (root == null || CollectionUtils.isEmpty(filters)) {
			return restrictions;
		}
		restrictions = buildPredicate(root, criteriaBuilder, restrictions, filters);
		return restrictions;
	}

	private Predicate buildPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, Predicate restrictions, List<Filter> filters) {
		for (Filter filter : filters) {
			if (filter == null || !filter.isSearchEnable()) {
				continue;
			}
			String property = filter.getProperty();
			Filter.Operator operator = filter.getOperator();
			operator = operator==null?Operator.EQ:operator;
			Object value = filter.getValue();
			Boolean ignoreCase = filter.getIgnoreCase();
			Path<?> path = getPath(root, property);
			if (path == null) {
				continue;
			}
			switch (operator) {
				case EQ:
					if (value != null) {
						if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
							restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
						} else {
							restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(path, value));
						}
					} else {
						restrictions = criteriaBuilder.and(restrictions, path.isNull());
					}
					break;
				case NE:
					if (value != null) {
						if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
							restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
						} else {
							restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(path, value));
						}
					} else {
						restrictions = criteriaBuilder.and(restrictions, path.isNotNull());
					}
					break;
				case GT:
					if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt((Path<Number>) path, (Number) value));
					} else if (Date.class.isAssignableFrom(path.getJavaType()) && value instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThan((Path<Date>) path, (Date) value));
					}
					break;
				case LT:
					if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt((Path<Number>) path, (Number) value));
					} else if (Date.class.isAssignableFrom(path.getJavaType()) && value instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThan((Path<Date>) path, (Date) value));
					}
					break;
				case GE:
					if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge((Path<Number>) path, (Number) value));
					} else if (Date.class.isAssignableFrom(path.getJavaType()) && value instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo((Path<Date>) path, (Date) value));
					}
					break;
				case LE:
					if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le((Path<Number>) path, (Number) value));
					} else if (Date.class.isAssignableFrom(path.getJavaType()) && value instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo((Path<Date>) path, (Date) value));
					}
					break;
				case LIKE:
					if (String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
						if (BooleanUtils.isTrue(ignoreCase)) {
							restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
						} else {
							restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like((Path<String>) path, ((String) value)));
						}
					}
					break;
				case IN:
					if(value instanceof Collection) {
						restrictions = criteriaBuilder.and(restrictions, path.in((Collection)value));
					}else if(value.getClass().isArray()) {
						restrictions = criteriaBuilder.and(restrictions, path.in((Object[])value));
					}else {
						restrictions = criteriaBuilder.and(restrictions, path.in(value));
					}
					break;
				case NOT_IN:
					if(value instanceof Collection) {
						restrictions = criteriaBuilder.and(restrictions, path.in((Collection)value).not());
					}else if(value.getClass().isArray()) {
						restrictions = criteriaBuilder.and(restrictions, path.in((Object[])value).not());
					}else {
						restrictions = criteriaBuilder.and(restrictions, path.in(value).not());
					}
					break;
				case IS_NULL:
					restrictions = criteriaBuilder.and(restrictions, path.isNull());
					break;
				case IS_NOT_NULL:
					restrictions = criteriaBuilder.and(restrictions, path.isNotNull());
					break;
				case OR:
					//restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(buildPredicate(root, criteriaBuilder, criteriaBuilder.conjunction(), (List<Filter>)value)));
					restrictions = criteriaBuilder.or(buildPredicate(root, criteriaBuilder, restrictions, (List<Filter>)value));
					//restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(buildPredicate(root, criteriaBuilder, restrictions, (List<Filter>)value)));
					break;
				case JOIN:
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join(property), value));
					break;
			}
		}
		return restrictions;
	}

	/**
	 * 转换为Order
	 *
	 * @param root
	 *            Root
	 * @param orders
	 *            排序
	 * @return Order
	 */
	private List<javax.persistence.criteria.Order> toOrders(Root<T> root, List<Order> orders) {
		List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
		if (root == null || CollectionUtils.isEmpty(orders)) {
			return orderList;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		for (Order order : orders) {
			if (order == null || !order.isOrderEnable()) {
				continue;
			}
			String property = order.getProperty();
			Order.Direction direction = order.getDirection();
			Path<?> path = getPath(root, property);
			if (path == null || direction == null) {
				continue;
			}
			switch (direction) {
				case ASC:
					orderList.add(criteriaBuilder.asc(path));
					break;
				case DESC:
					orderList.add(criteriaBuilder.desc(path));
					break;
			}
		}
		return orderList;
	}
	
	/**
	 * 获取Field
	 * 
	 * @param type
	 *            类
	 * @return Field
	 */
	public static Field[] getDeclaredFields(Class<?> type) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");

		Field[] result = DECLARED_FIELDS_CACHE.get(type);
		if (result == null) {
			result = type.getDeclaredFields();
			DECLARED_FIELDS_CACHE.put(type, result != null ? result : new Field[0]);
		}
		return result;
	}
	
	/**
	 * 查找Field
	 * 
	 * @param type
	 *            类
	 * @param annotationType
	 *            Annotation类
	 * @return Field，包含父类Field
	 */
	public static List<Field> findFields(Class<?> type, Class<? extends Annotation> annotationType) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");
		Assert.notNull(annotationType, "[Assertion failed] - annotationType is required; it must not be null");

		List<Field> result = new ArrayList<>();
		Class<?> targetClass = type;
		while (targetClass != null && !Object.class.equals(targetClass)) {
			for (Field field : getDeclaredFields(targetClass)) {
				if (getAnnotation(field, annotationType) != null) {
					result.add(field);
				}
			}
			targetClass = targetClass.getSuperclass();
		}
		return result;
	}
	
	/**
	 * 获取Annotation
	 * 
	 * @param annotatedElement
	 *            Annotation元素
	 * @param annotationType
	 *            Annotation类
	 * @return Annotation
	 */
	private static <A extends Annotation> A getAnnotation(AnnotatedElement annotatedElement, Class<A> annotationType) {
		Assert.notNull(annotatedElement, "[Assertion failed] - annotatedElement is required; it must not be null");
		Assert.notNull(annotationType, "[Assertion failed] - annotationType is required; it must not be null");

		A annotation = annotatedElement.getAnnotation(annotationType);
		if (annotation == null) {
			for (Annotation metaAnnotation : annotatedElement.getAnnotations()) {
				annotation = metaAnnotation.annotationType().getAnnotation(annotationType);
				if (annotation != null) {
					break;
				}
			}
		}
		return annotation;
	}
}
