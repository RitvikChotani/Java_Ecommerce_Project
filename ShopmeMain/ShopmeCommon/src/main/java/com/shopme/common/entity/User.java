package com.shopme.common.entity;

import java.beans.Transient;
import java.util.HashSet;
import java.util.Set;

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

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + ", photos=" + photos + ", enabled=" + enabled + ", roles=" + roles + "]";
	}

	@Column(name="first_name", length = 40, nullable = false)
	private String firstName;
	
	@Column(name="last_name", length = 40, nullable = false)
	private String lastName;
	
	@Column(length = 64, nullable = false)
	private String password;
	
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	
	@Column(length = 64)	
	private String photos;
	
	@Column(nullable = false)
	private boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="users_roles", 
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name ="role_id"))
	private Set<Role> roles = new HashSet<Role>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public User(String firstName, String lastName, String password, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}
	
	public void addRole(Role role) {
		this.roles.add(role); 
	}
	
	public User() {
		super();
	}
	
	@Transient
	public String getPhotoImagePath() {
		if(id==null || photos == null) {
			return "/user-photos/user.png";
			
		}
		
		return "/user-photos/" + this.id + "/" + this.photos;
	}
	
	@Transient
	public String getFullName() {
		return firstName + " " + lastName;
	}
}


