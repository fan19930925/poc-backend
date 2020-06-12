package com.joole.domain;
// Generated Nov 20, 2019 3:12:05 PM by Hibernate Tools 5.2.12.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "[User]", schema = "dbo", catalog = "joole")
public class User implements java.io.Serializable {

	private int id;
	private String username;
	private String password;
	private String sessionToken;

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "username", length = 45)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public String getSessionToken() {
		return sessionToken;
	}



	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
}
