package com.application.service;

import com.application.model.Role;

public interface RoleService {

	public Long countByName(String name);

	public Role save(Role role);
}
