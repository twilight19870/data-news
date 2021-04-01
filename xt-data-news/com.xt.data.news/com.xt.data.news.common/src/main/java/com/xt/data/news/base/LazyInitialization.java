package com.xt.data.news.base;

/**
 * 懒加载初始化
 */
public interface LazyInitialization<T extends BaseEntity> {

	public void lazy(T entity);
}
