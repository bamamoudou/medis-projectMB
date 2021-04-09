package com.mediscreen.msclientui.serviceImpl;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mediscreen.msclientui.exception.EmptyDataException;
import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.model.Jwt;
import com.mediscreen.msclientui.model.Login;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import com.mediscreen.msclientui.service.SecurityService;

public class SecurityServiceImpl implements SecurityService {
	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * Zuul proxy
	 */
	@Autowired
	private MSZuulProxy msZuulProxy;

	/**
	 * Constructor
	 */
	public SecurityServiceImpl() {
	}

	/**
	 * Constructor
	 * 
	 * @param msZuulProxy
	 */
	public SecurityServiceImpl(MSZuulProxy msZuulProxy) {
		this.msZuulProxy = msZuulProxy;
	}

	/**
	 * @see SecurityService {@link #authenticationCheck(String)}
	 */
	@Override
	public boolean authenticationCheck(String token) {
		if (StringUtils.isBlank(token)) {
			logger.error("The authentication token is required");
			throw new EmptyDataException("The authentication token is required");
		}
		ResponseEntity<Void> validation = msZuulProxy.msAuthenticationValidateToken(token);
		if (!validation.getStatusCode().equals(HttpStatus.OK)) {
			logger.error("Permission denied");
			throw new NotAllowedException("Permission denied");
		}
		return true;
	}

	/**
	 * @see SecurityService {@link #isLog(HttpSession)}
	 */
	@Override
	public boolean isLog(HttpSession session) {
		String token = (String) session.getAttribute("token");
		if (token != null && !StringUtils.isBlank(token))
			return authenticationCheck(token);
		return false;
	}

	/**
	 * @see SecurityService {@link #logUser(Login)}
	 */
	@Override
	public String logUser(Login login) {
		if (!StringUtils.isBlank(login.getUsername()) && !StringUtils.isBlank(login.getPassword())) {
			try {
				ResponseEntity<Jwt> jwt = msZuulProxy.msAuthenticationGenerateToken(login);
				if (jwt != null && jwt.getStatusCode().equals(HttpStatus.OK) && jwt.getBody() != null
						&& jwt.getBody().getToken() != null) {
					return jwt.getBody().getToken();
				} else {
					logger.error("JWT no generated");
					throw new NullPointerException("JWT no generated");
				}
			} catch (NotAllowedException e) {
				logger.error("Permission denied, username or password are incorrect");
				throw new NotAllowedException("Permission denied, username or password are incorrect");
			}
		} else {
			logger.error("Username and password are required");
			throw new EmptyDataException("Username and password are required");
		}
	}
}