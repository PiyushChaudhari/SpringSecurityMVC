package com.application.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.application.base.persistance.AbstractHibernateDao;
import com.application.model.Role;

@Repository
public class RoleDaoImpl extends AbstractHibernateDao<Role> implements RoleDao<Role> {

	public RoleDaoImpl() {
		setClazz(Role.class);
	}

	@Override
	public Long countByName(String name) {
		Query query = getAbstractHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery("select count(*) from com.application.model.Role where name = :name");
		query.setParameter("name", name);
		return (Long) query.uniqueResult();
	}

	@Override
	public Role saveRole(Role entity) {
		return save(entity);
	}
}
