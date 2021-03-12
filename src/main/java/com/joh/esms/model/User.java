package com.joh.esms.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_USER")
	private Integer id;

	@Column(name = "USER_NAME", unique = true, nullable = false)
	private String userName;
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	@Column(name = "ENABLED", nullable = false)
	private boolean enabled;

	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "USER_ROLE", inverseJoinColumns = @JoinColumn(name = "I_ROLE"), joinColumns = @JoinColumn(name = "I_USER"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "I_ROLE", "I_USER" }) })
	private List<Role> roles;

	public Integer getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", enabled=" + enabled
				+ ", roles=" + roles + "]";
	}

}
