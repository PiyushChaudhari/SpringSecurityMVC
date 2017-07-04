package com.application.dao;

import java.io.Serializable;

import com.application.model.Role;

public interface RoleDao<T extends Serializable> {

	public Long countByName(String name);

	public Role saveRole(Role role);
	
	public Role getRole(String id);
}
