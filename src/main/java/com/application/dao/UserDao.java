package com.application.dao;

import java.io.Serializable;
import java.util.List;

import com.application.model.User;
import com.application.taglib.pagination.Paginator;

public interface UserDao<T extends Serializable> {

	public User findByEmailAndPassword(String email, String password);

	public User findByEmail(String email);

	public User saveUser(User user);

	public void updateUser(User user);

	public List<User> getAllUser();
	
	public List<User> getAllUser(Paginator paginator);
	
	public Integer getAllUserTotal();

	public User getUser(String id);

	public void deleteUser(User user);

	public Long countByEmail(String email);
}
