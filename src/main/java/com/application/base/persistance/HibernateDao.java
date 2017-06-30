package com.application.base.persistance;

import java.io.Serializable;
import java.util.List;

public interface HibernateDao<T extends Serializable> {

	// T get(final String id);
	//
	// void create(final T entity);
	//
	// T save(final T entity);
	//
	// void delete(final T entity);
	//
	// void deleteById(final String entityId);
	//
	// List<T> findAll();

	T get(final String id);

	T load(final String id);

	void persist(final T entity);

	T save(final T entity);

	void saveOrUpdate(final T entity);

	void update(final T entity);

	T merge(final T entity);

	void delete(final T entity);

	List<T> list();
}
