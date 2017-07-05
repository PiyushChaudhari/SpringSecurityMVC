package com.application.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Role")
public class Role extends BaseDomain {

	private static final long serialVersionUID = 1L;
	private String name;

	@ManyToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private Collection<User> users;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name = "Role_Permissions",joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "permission_id", referencedColumnName = "id") })
	private Set<Permission> permissions = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public User getUser() {
	// return user;
	// }
	//
	// public void setUser(User user) {
	// this.user = user;
	// }

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(final Collection<User> users) {
		this.users = users;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	// public void setPermissions(Set<Permission> permissions) {
	// this.permissions = permissions;
	// }

	public void addPermission(Permission permission) {
		this.permissions.add(permission);
		permission.setRole(this);
	}

	public void removePermission(Permission permission) {
		this.permissions.remove(permission);
		permission.setRole(null);
	}

}