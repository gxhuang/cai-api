package com.cai.service.base;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.cai.bean.Page;

@Transactional
public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
	@PersistenceContext
	protected EntityManager em;

	protected Class<T> type;

	public void clear() {
		em.clear();
	}

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		this.type = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
	@Transactional(rollbackFor=Exception.class) 
	public void delete(PK entityid) {
		em.remove(em.getReference(this.type, entityid));
	}
	@Transactional(rollbackFor=Exception.class) 
	public void delete(PK[] entityids) {
		for (Object id : entityids) {
			em.remove(em.getReference(this.type, id));
		}
	}
	@Transactional(rollbackFor=Exception.class) 
	public void save(T entity) {
		em.persist(entity);
	}
	@Transactional(rollbackFor=Exception.class) 
	public T update(T entity) {
		return em.merge(entity);
	}

	public T find(PK entityId) {
		return em.find(this.type, entityId);
	}

	
	public Page<T> pagination(int currentPage, int pageSize, LinkedHashMap<String, String> orderby) {
		return pagination(currentPage, pageSize, null, null, orderby);
	}

	
	public Page<T> pagination(int currentPage, int pageSize, String wherejpql, Object[] queryParams) {
		return pagination(currentPage, pageSize, wherejpql, queryParams, null);
	}

	
	public Page<T> pagination(int currentPage, int pageSize) {
		return pagination(currentPage, pageSize, null, null, null);
	}

	@SuppressWarnings("unchecked")	
	public Page<T> pagination(int currentPage, int pageSize, String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		String entityname = getEntityName(this.type);
		Query query = em.createQuery("select count(" + getCountField(this.type) + ") from " + entityname + " o "
				+ (wherejpql == null ? "" : "where " + wherejpql));
		setQueryParams(query, queryParams);
		Page<T> page = new Page<T>(currentPage, (Long) query.getSingleResult());

		query = em.createQuery("select o from " + entityname + " o " + (wherejpql == null ? "" : "where " + wherejpql)
				+ buildOrderby(orderby));
		setQueryParams(query, queryParams);
		query.setFirstResult(page.getStartIndex()).setMaxResults(page.getPageSize());
		page.setRecords(query.getResultList());

		return page;
	}

	protected void setQueryParams(Query query, Object[] queryParams) {
		if (queryParams != null && queryParams.length > 0) {
			for (int i = 0; i < queryParams.length; i++) {
				query.setParameter(i + 1, queryParams[i]);
			}
		}
	}

	/**
	 * 组装order by语句
	 * 
	 * @param orderby
	 * @return
	 */
	protected String buildOrderby(LinkedHashMap<String, String> orderby) {
		StringBuffer orderbyql = new StringBuffer("");
		if (orderby != null && orderby.size() > 0) {
			orderbyql.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length() - 1);
		}
		return orderbyql.toString();
	}

	/**
	 * 获取实体的名称
	 * 
	 * @param <T>
	 * @param entityClass
	 *            实体类
	 * @return
	 */
	protected String getEntityName(Class<T> entityClass) {
		String entityname = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityname = entity.name();
		}
		return entityname;
	}

	protected String getCountField(Class<T> clazz) {
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for (PropertyDescriptor propertydesc : propertyDescriptors) {
				Method method = propertydesc.getReadMethod();
				if (method != null && method.isAnnotationPresent(EmbeddedId.class)) {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(propertydesc.getPropertyType())
							.getPropertyDescriptors();
					out = "o." + propertydesc.getName() + "."
							+ (!ps[1].getName().equals("class") ? ps[1].getName() : ps[0].getName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

}
