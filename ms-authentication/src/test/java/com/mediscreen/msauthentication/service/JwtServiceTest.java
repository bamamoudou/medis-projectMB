package com.mediscreen.msauthentication.service;

import com.mediscreen.msauthentication.JWTTest;
import com.mediscreen.msauthentication.configuration.AppProperties;
import com.mediscreen.msauthentication.exception.NotAllowedException;
import com.mediscreen.msauthentication.model.Jwt;
import com.mediscreen.msauthentication.serviceImpl.JwtServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
	private String token = JWTTest.token;
	private String wrongToken = JWTTest.wrongToken;

	@Mock
	private static AppProperties appProperties;

	private JwtServiceImpl jwtService;

	@BeforeEach
	void int_test() {
		jwtService = new JwtServiceImpl(appProperties);
	}

	@Tag("JwtServiceTest")
	@Test
	void parseJWT_test() {
		when(appProperties.getJwtSecret()).thenReturn("mediscreen-jwt-secret");
		assertThat(jwtService.parseJWT(token)).isNotNull();
		assertThatExceptionOfType(NotAllowedException.class).isThrownBy(() -> jwtService.parseJWT(wrongToken));
	}

	@Tag("JwtServiceTest")
	@Test
	void createJWT_test() {
		when(appProperties.getJwtSecret()).thenReturn("mediscreen-jwt-secret");

		UUID uuid = UUID.randomUUID();
		Map<String, Object> claims = new HashMap<>();
		claims.put("userID", uuid.toString());
		claims.put("username", "username");
		Jwt newToken = jwtService.createJWT(uuid, "Login", "Mediscreen", claims, (long) 60 * 60 * 24);

		assertThat(newToken.getGenerationDate()).isNotNull();
		assertThat(newToken.getExpirationDate()).isNotNull();
		assertThat(newToken.getExpirationDate()).isAfterOrEqualTo(newToken.getGenerationDate());
		assertThat(newToken.getToken()).isNotNull();
		assertThat(newToken.getToken()).isNotBlank();
	}
}