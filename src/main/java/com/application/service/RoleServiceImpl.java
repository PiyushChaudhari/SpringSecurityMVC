package com.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.dao.RoleDao;
import com.application.model.Role;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao<Role> roleDao;

	@Override
	public Long countByName(String name) {
		return roleDao.countByName(name);
	}

	@Override
	public Role save(Role role) {
		return roleDao.saveRole(role);
	}

	@Override
	public Role get(String id) {
		return roleDao.getRole(id);
	}

}
