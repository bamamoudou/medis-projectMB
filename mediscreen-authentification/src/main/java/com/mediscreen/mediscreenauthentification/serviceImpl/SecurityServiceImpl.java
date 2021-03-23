package com.mediscreen.mediscreenauthentification.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mediscreen.mediscreenauthentification.config.AppProperties;
import com.mediscreen.mediscreenauthentification.exception.NotAllowedException;
import com.mediscreen.mediscreenauthentification.exception.NotFoundException;
import com.mediscreen.mediscreenauthentification.model.Jwt;
import com.mediscreen.mediscreenauthentification.model.Login;
import com.mediscreen.mediscreenauthentification.service.JwtService;
import com.mediscreen.mediscreenauthentification.service.SecurityService;

public class SecurityServiceImpl implements SecurityService {
	/**
	 * Token Service
	 */
	private JwtService jwtService;

	/**
	 * Password manager
	 */
	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * App properties
	 */
	private AppProperties appProperties;

	/**
	 * Constructor
	 * 
	 * @param jwtService
	 */
	public SecurityServiceImpl(JwtService jwtService, BCryptPasswordEncoder passwordEncoder,
			AppProperties appProperties) {
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.appProperties = appProperties;
	}

	/**
	 * @see SecurityServiceInterface {@link #logUser(Login)}
	 */
	public Jwt logUser(Login login) {
		if (appProperties.getUsername() != null && appProperties.getPassword() != null) {
			if (appProperties.getUsername().equals(login.getUsername())
					&& passwordEncoder.matches(login.getPassword(), appProperties.getPassword())) {
				UUID uuid = UUID.randomUUID();
				Map<String, Object> claims = new HashMap<>();
				claims.put("userID", uuid.toString());
				claims.put("username", login.getPassword());
				Long time = (long) 60 * 60 * 24;
				if (login.isRememberUser())
					time *= 90;
				return jwtService.createJWT(uuid, "Login", "Mediscreen", claims, time);
			} else {
				throw new NotAllowedException("Username and/or password are incorrect");
			}
		} else {
			throw new NotFoundException("User login informations are incomplete");
		}
	}

	/**
	 * @see SecurityServiceInterface {@link #isLog(String)}
	 */
	public boolean isLog(String token) {
		return ((token != null) && (jwtService.parseJWT(token) != null));
	}
}