package com.mediscreen.mediscreenauthentification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mediscreen.mediscreenauthentification.JWTTest;
import com.mediscreen.mediscreenauthentification.config.AppProperties;
import com.mediscreen.mediscreenauthentification.exception.NotAllowedException;
import com.mediscreen.mediscreenauthentification.exception.NotFoundException;
import com.mediscreen.mediscreenauthentification.model.Jwt;
import com.mediscreen.mediscreenauthentification.model.Login;
import com.mediscreen.mediscreenauthentification.serviceImpl.SecurityServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceTest {
	private String token = JWTTest.token;
	private String wrongToken = JWTTest.wrongToken;

	private Login login;

	@Mock
	private static JwtService jwtService;

	@Mock
	private static AppProperties appProperties;

	private SecurityServiceImpl securityService;

	@BeforeEach
	void init_test() {
		securityService = new SecurityServiceImpl(jwtService, new BCryptPasswordEncoder(), appProperties);

		login = new Login("username", "password", false);
	}

	@Tag("SecurityServiceTest")
	@Test
	void isLog_test() {
		when(jwtService.parseJWT(token)).thenReturn(JWTTest.claims);
		when(jwtService.parseJWT(wrongToken)).thenReturn(null);

		assertThat(securityService.isLog(token)).isTrue();
		assertThat(securityService.isLog(wrongToken)).isFalse();
	}

	@Tag("SecurityServiceTest")
	@Test
	void logUser_test_appPropertiesEmpty_username() {
		when(appProperties.getUsername()).thenReturn("username");
		when(appProperties.getPassword()).thenReturn(null);

		assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> securityService.logUser(login));
	}

	@Tag("SecurityServiceTest")
	@Test
	void logUser_test_appPropertiesEmpty_password() {
		when(appProperties.getUsername()).thenReturn(null);

		assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> securityService.logUser(login));
	}

	@Tag("SecurityServiceTest")
	@Test
	void logUser_test_loginEmpty_username() {
		when(appProperties.getUsername()).thenReturn("username");
		when(appProperties.getPassword()).thenReturn("password");
		login.setUsername(null);

		assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> securityService.logUser(login));
	}

	@Tag("SecurityServiceTest")
	@Test
	void logUser_test_loginEmpty_password() {
		when(appProperties.getUsername()).thenReturn("username");
		when(appProperties.getPassword()).thenReturn("password");
		login.setPassword(null);

		assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> securityService.logUser(login));
	}

	@Tag("SecurityServiceTest")
	@Test
	void logUser_test_WrongUsername() {
		when(appProperties.getUsername()).thenReturn("username");
		when(appProperties.getPassword()).thenReturn("password");
		login.setUsername("wrongUsername");

		assertThatExceptionOfType(NotAllowedException.class).isThrownBy(() -> securityService.logUser(login));
	}

	@Tag("SecurityServiceTest")
	@Test
	void logUser_test_WrongPassword() {
		when(appProperties.getUsername()).thenReturn("username");
		when(appProperties.getPassword()).thenReturn("$2a$10$gm7I1D4XJ8hBIQxScnSwqelQfNKPnm/ifw7FXcsn4kTDxBsBFtyza");
		login.setPassword("wrongPassword");

		assertThatExceptionOfType(NotAllowedException.class).isThrownBy(() -> securityService.logUser(login));
	}

	@Tag("SecurityServiceTest")
	@Test
	void logUser_test() {
		when(appProperties.getUsername()).thenReturn("username");
		when(appProperties.getPassword()).thenReturn("$2a$10$gm7I1D4XJ8hBIQxScnSwqelQfNKPnm/ifw7FXcsn4kTDxBsBFtyza");
		when(jwtService.createJWT(any(UUID.class), anyString(), anyString(), any(Map.class), any(Long.class)))
				.thenReturn(new Jwt(JWTTest.token, LocalDateTime.now(), LocalDateTime.now()));

		Jwt generateJwt = securityService.logUser(login);
		assertThat(generateJwt.getGenerationDate()).isNotNull();
		assertThat(generateJwt.getExpirationDate()).isNotNull();
		assertThat(generateJwt.getExpirationDate()).isAfterOrEqualTo(generateJwt.getGenerationDate());
		assertThat(generateJwt.getToken()).isNotNull();
		assertThat(generateJwt.getToken()).isNotBlank();
	}
}