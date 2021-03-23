package com.mediscreen.mediscreenclientui.serviceImpl;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mediscreen.mediscreenclientui.exception.EmptyDataException;
import com.mediscreen.mediscreenclientui.exception.NotAllowedException;
import com.mediscreen.mediscreenclientui.model.Jwt;
import com.mediscreen.mediscreenclientui.model.Login;
import com.mediscreen.mediscreenclientui.proxy.MSZuulProxy;
import com.mediscreen.mediscreenclientui.service.SecurityService;

public class SecurityServiceImpl implements SecurityService {
	/**
	 * Zuul proxy
	 */
	@Autowired
	private MSZuulProxy msZuulProxy;

	public SecurityServiceImpl() {
	}

	/**
	 * @see SecurityServiceInterface {@link #authenticationCheck(String)}
	 */
	@Override
	public boolean authenticationCheck(String token) {
		if (StringUtils.isBlank(token))
			throw new EmptyDataException("The authentication token is required");
		ResponseEntity<Void> validation = msZuulProxy.msAuthentication_validateToken(token);
		if (!validation.getStatusCode().equals(HttpStatus.OK))
			throw new NotAllowedException("Permission denied");
		return true;
	}

	/**
	 * @see SecurityServiceInterface {@link #isLog(HttpSession)}
	 */
	@Override
	public boolean isLog(HttpSession session) {
		String token = (String) session.getAttribute("token");
		if (token != null && !StringUtils.isBlank(token))
			return authenticationCheck(token);
		return false;
	}

	/**
	 * @see SecurityServiceInterface {@link #logUser(Login, HttpSession)}
	 */
	@Override
	public String logUser(Login login, HttpSession session) {
		if (!StringUtils.isBlank(login.getUsername()) && !StringUtils.isBlank(login.getPassword())) {
			try {
				ResponseEntity<Jwt> jwt = msZuulProxy.msAuthentication_generateToken(login);
				if (jwt.getStatusCode().equals(HttpStatus.OK) && jwt.getBody() != null
						&& jwt.getBody().getToken() != null) {
					return jwt.getBody().getToken();
				} else {
					throw new NullPointerException("JWT no generated");
				}
			} catch (NotAllowedException e) {
				throw new NotAllowedException("Permission denied, username or password are incorrect");
			}
		} else {
			throw new EmptyDataException("Username and password are required");
		}
	}
}