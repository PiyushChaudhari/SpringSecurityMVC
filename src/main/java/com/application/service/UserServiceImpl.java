package com.application.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.componant.CustomAuthenticationProvider;
import com.application.dao.UserDao;
import com.application.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	public UserDao<User> userDao;

	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;

	@Override
	public User findByEmailAndPassword(String email, String password) {
		return userDao.findByEmailAndPassword(email, password);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public User save(User user) {
		return userDao.saveUser(user);
	}

	@Override
	public List<User> getAllUser() {
		return userDao.getAllUser();
	}

	@Override
	public User get(String id) {
		return userDao.getUser(id);
	}

	@Override
	public void update(User user) {
		User u = userDao.getUser(user.getId());
		if (u.getVersion() > user.getVersion())
			throw new RuntimeException("Row Updated");
		BeanUtils.copyProperties(user, u, "passwordHash", "userType", "role");
		userDao.updateUser(u);
	}

	@Override
	public void delete(String id) {
		User u = userDao.getUser(id);
		if (u != null)
			userDao.deleteUser(u);
	}

	@Override
	public User getLoggedInUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@Override
	public Long countByEmail(String email) {
		return userDao.countByEmail(email);
	}

	@Override
	public void updateLoggedInUser(User user) {
		User u = getLoggedInUser();
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());

		Authentication request = new UsernamePasswordAuthenticationToken(u.getEmail(), u.getPasswordHash(),
				u.getAuthorities());
		Authentication result = customAuthenticationProvider.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(result);
	}

}
