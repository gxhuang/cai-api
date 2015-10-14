package com.cai.service.base;

import java.io.Serializable;
import java.util.LinkedHashMap;

import com.cai.bean.Page;

public interface GenericDao<T, PK extends Serializable> {

	/**
	 * 清除一级缓存的数据
	 */
	public void clear();

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体id
	 */
	public void save(T entity);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体id
	 */
	public T update(T entity);

	/**
	 * 删除实体
	 * 
	 * @param entityClass
	 *            实体类
	 * @param entityid
	 *            实体id
	 */
	public void delete(PK entityid);

	/**
	 * 删除实体
	 * 
	 * @param entityClass
	 *            实体类
	 * @param entityids
	 *            实体id数组
	 */
	public void delete(PK[] entityids);

	/**
	 * 获取实体
	 * 
	 * @param <T>
	 * @param entityClass
	 *            实体类
	 * @param entityId
	 *            实体id
	 * @return
	 */
	public T find(PK entityId);

	/**
	 * 获取分页数据
	 * 
	 * @param <T>
	 * 
	 * @param currentPage
	 *            开始页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public Page<T> pagination(int currentPage, int pageSize, String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby);

	public Page<T> pagination(int currentPage, int pageSize, String wherejpql, Object[] queryParams);

	public Page<T> pagination(int currentPage, int pageSize, LinkedHashMap<String, String> orderby);

	public Page<T> pagination(int currentPage, int pageSize);

}
