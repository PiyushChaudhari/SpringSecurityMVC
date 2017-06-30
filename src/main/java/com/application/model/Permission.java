package com.application.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Permission")
public class Permission extends BaseDomain {

	private static final long serialVersionUID = 1L;

	String name;

	@ManyToOne(cascade = CascadeType.ALL)
	Role role;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
