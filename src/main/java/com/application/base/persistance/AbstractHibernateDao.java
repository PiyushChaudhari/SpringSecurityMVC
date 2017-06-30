package com.application.base.persistance;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class AbstractHibernateDao<T extends Serializable> extends HibernateDaoSupport implements HibernateDao<T> {

	private Class<T> clazz;

	protected final void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	@Autowired
	public void init(SessionFactory factory) {
		setSessionFactory(factory);
	}

	public HibernateTemplate getAbstractHibernateTemplate() {
		return getHibernateTemplate();
	}

	@Override
	public T get(String id) {
		return getAbstractHibernateTemplate().get(clazz, id);
	}

	@Override
	public T load(String id) {
		return getAbstractHibernateTemplate().load(clazz, id);
	}

	@Override
	public void persist(T entity) {
		getAbstractHibernateTemplate().persist(entity);
	}

	@Override
	public T save(T entity) {
		getAbstractHibernateTemplate().save(entity);
		return entity;
	}

	@Override
	public void saveOrUpdate(T entity) {
		getAbstractHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void update(T entity) {
		getAbstractHibernateTemplate().update(entity);
	}

	@Override
	public T merge(T entity) {
		return getAbstractHibernateTemplate().merge(entity);
	}

	@Override
	public void delete(T entity) {
		getAbstractHibernateTemplate().delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> list() {
		return getAbstractHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery("from " + clazz.getName()).list();
	}
}
