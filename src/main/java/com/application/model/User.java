package com.application.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.application.enums.UserType;

@Entity
@Table(name = "User")
public class User extends BaseDomain implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String email;
	private String passwordHash;

	@Enumerated(EnumType.STRING)
	private UserType userType;


@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "User_Roles", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private Collection<Role> role = new ArrayList<>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Collection<Role> getRole() {
		return role;
	}

	public void setRole(Collection<Role> role) {
		this.role = role;
	}

	public void addRole(Role role) {
		this.role.add(role);
//		role.setUser(this);
	}

	public void removeRole(Role role) {
		this.role.add(role);
//		role.setUser(null);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<Permission> collection = new ArrayList<>();
		for (Role roleInstance : this.role) {
			collection.addAll(roleInstance.getPermissions());
		}
		for (Permission item : collection) {
			authorities.add(new SimpleGrantedAuthority(item.getName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.getPasswordHash();
	}

	@Override
	public String getUsername() {
		return this.getEmail();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof User) {
			return getUsername().equals(((User) rhs).getUsername());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getUsername().hashCode();
	}

}