package com.mediscreen.msauthentication.serviceImpl;

import com.mediscreen.msauthentication.configuration.AppProperties;
import com.mediscreen.msauthentication.exception.NotAllowedException;
import com.mediscreen.msauthentication.exception.NotFoundException;
import com.mediscreen.msauthentication.model.Jwt;
import com.mediscreen.msauthentication.model.Login;
import com.mediscreen.msauthentication.services.JwtService;
import com.mediscreen.msauthentication.services.SecurityService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SecurityServiceImpl implements SecurityService {
	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * Token Service
	 */
	private JwtService jwtServiceInterface;

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
	public SecurityServiceImpl(JwtService jwtServiceInterface, BCryptPasswordEncoder passwordEncoder,
			AppProperties appProperties) {
		this.jwtServiceInterface = jwtServiceInterface;
		this.passwordEncoder = passwordEncoder;
		this.appProperties = appProperties;
	}

	/**
	 * @see SecurityService {@link #logUser(Login)}
	 */
	public Jwt logUser(Login login) {
		if (appProperties.getUsername() != null && appProperties.getPassword() != null && login.getUsername() != null
				&& login.getPassword() != null) {
			if (appProperties.getUsername().equals(login.getUsername())
					&& passwordEncoder.matches(login.getPassword(), appProperties.getPassword())) {
				UUID uuid = UUID.randomUUID();
				Map<String, Object> claims = new HashMap<>();
				claims.put("userID", uuid.toString());
				claims.put("username", login.getUsername());
				Long time = (long) 60 * 60 * 24;
				if (login.isRememberUser())
					time *= 90;
				return jwtServiceInterface.createJWT(uuid, "Login", "Mediscreen", claims, time);
			} else {
				logger.error("Username and/or password are incorrect");
				throw new NotAllowedException("Username and/or password are incorrect");
			}
		} else {
			logger.error("User login informations are incomplete");
			throw new NotFoundException("User login informations are incomplete");
		}
	}

	/**
	 * @see SecurityService {@link #isLog(String)}
	 */
	public boolean isLog(String token) {
		return ((token != null) && (jwtServiceInterface.parseJWT(token) != null));
	}
}