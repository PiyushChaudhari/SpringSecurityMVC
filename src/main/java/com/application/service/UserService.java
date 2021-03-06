package com.application.service;

import java.util.List;

import com.application.model.User;
import com.application.taglib.pagination.Paginator;

public interface UserService {

	public User findByEmailAndPassword(String email, String password);

	public User findByEmail(String email);

	public User save(User user);

	public List<User> getAllUser();
	
	public List<User> getAllUser(Paginator paginator);
	
	public Integer getAllUserTotal();

	public User get(String id);

	public void update(User user);

	public void delete(String id);

	public User getLoggedInUser();

	public Long countByEmail(String email);

	public void updateLoggedInUser(User user);

	public List<User> getAllLoggedInUser();

	public void expireSession(String id);
}
