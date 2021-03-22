package com.mediscreen.mediscreenauthentification.model;

import javax.validation.constraints.NotBlank;

public class Login {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	public Login(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Login() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}