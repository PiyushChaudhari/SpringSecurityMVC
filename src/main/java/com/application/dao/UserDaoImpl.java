package com.application.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.application.base.persistance.AbstractHibernateDao;
import com.application.model.User;
import com.application.taglib.pagination.Paginator;

@Repository
public class UserDaoImpl extends AbstractHibernateDao<User> implements UserDao<User> {

	public UserDaoImpl() {
		setClazz(User.class);
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		Query query = getAbstractHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery("from com.application.model.User where email = :email and passwordHash = :passwordHash");
		query.setParameter("email", email);
		query.setParameter("passwordHash", password);
		return (User) query.uniqueResult();
	}

	@Override
	public User findByEmail(String email) {
		Query query = getAbstractHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery("from com.application.model.User where email = :email");
		query.setParameter("email", email);
		return (User) query.uniqueResult();
	}

	@Override
	public User saveUser(User user) {
		return save(user);
	}

	@Override
	public List<User> getAllUser() {
		return list();
	}

	@Override
	public User getUser(String id) {
		return get(id);
	}

	@Override
	public void updateUser(User user) {
		update(user);
	}

	@Override
	public void deleteUser(User user) {
		delete(user);
	}

	@Override
	public Long countByEmail(String email) {
		Query query = getAbstractHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery("select count(*) from com.application.model.User where email = :email");
		query.setParameter("email", email);
		return (Long) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUser(Paginator paginator) {
		Query query = getAbstractHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery("from com.application.model.User");
		query.setFirstResult(paginator.getOffset());
		query.setMaxResults(paginator.getMax());
		return query.list();
	}

	@Override
	public Integer getAllUserTotal() {
		Query query = getAbstractHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery("select count(*) from com.application.model.User");
		return ((Long) query.uniqueResult()).intValue();
	}
}
