package com.mediscreen.mediscreenauthentification.model;

import java.time.LocalDateTime;

public class Jwt {
	private String token;
	private LocalDateTime generationDate;
	private LocalDateTime expirationDate;

	public Jwt(String token, LocalDateTime generationDate, LocalDateTime expirationDate) {
		this.token = token;
		this.generationDate = generationDate;
		this.expirationDate = expirationDate;
	}

	public Jwt() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getGenerationDate() {
		return generationDate;
	}

	public void setGenerationDate(LocalDateTime generationDate) {
		this.generationDate = generationDate;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}
}