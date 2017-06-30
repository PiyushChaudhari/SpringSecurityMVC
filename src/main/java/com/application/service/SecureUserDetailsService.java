package com.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.dao.UserDao;
import com.application.model.User;

@Transactional
@Service("secureUserDetailsService")
public class SecureUserDetailsService implements UserDetailsService {

	@Autowired
	UserDao<User> userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByEmail(username);
		if (user == null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		return user;
		// return new
		// org.springframework.security.core.userdetails.User(user.getEmail(),
		// user.getPasswordHash(), true,
		// true, true, true, user.getAuthorities());
	}

}
