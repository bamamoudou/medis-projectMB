package com.mediscreen.msclientui.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class JwtTest {
	private Jwt jwt;
	private LocalDateTime generationDate = LocalDateTime.now();
	private LocalDateTime expirationDate = LocalDateTime.now();

	@BeforeEach
	void init_test() {
		jwt = new Jwt("token", generationDate, expirationDate);
	}

	@Tag("JwtTest")
	@Test
	void get_test() {
		assertThat(jwt.getToken()).isEqualTo("token");
		assertThat(jwt.getGenerationDate()).isEqualTo(generationDate);
		assertThat(jwt.getExpirationDate()).isEqualTo(expirationDate);
	}

	@Tag("JwtTest")
	@Test
	void set_test() {
		assertThat(jwt.getToken()).isEqualTo("token");
		assertThat(jwt.getGenerationDate()).isEqualTo(generationDate);
		assertThat(jwt.getExpirationDate()).isEqualTo(expirationDate);

		LocalDateTime newGenerationDate = LocalDateTime.now();
		LocalDateTime newExpirationDate = LocalDateTime.now();

		jwt.setToken("newToken");
		jwt.setGenerationDate(newGenerationDate);
		jwt.setExpirationDate(newExpirationDate);

		assertThat(jwt.getToken()).isEqualTo("newToken");
		assertThat(jwt.getGenerationDate()).isEqualTo(newGenerationDate);
		assertThat(jwt.getExpirationDate()).isEqualTo(newExpirationDate);
	}
}