package com.mediscreen.mediscreenclientui.serviceImpl;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mediscreen.mediscreenclientui.exception.EmptyDataException;
import com.mediscreen.mediscreenclientui.exception.NotAllowedException;
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
	 * @see SecurityService {@link #authenticationCheck(String)}
	 */
	@Override
	public void authenticationCheck(String token) {
		if (StringUtils.isBlank(token))
			throw new EmptyDataException("The authentication token is required");
		ResponseEntity<Void> validation = msZuulProxy.msAuthentication_validateToken(token);
		if (!validation.getStatusCode().equals(HttpStatus.OK))
			throw new NotAllowedException("Permission denied");
	}

	/**
	 * @see SecurityServiceInterface {@link #isLog(HttpSession)}
	 */
	@Override
	public boolean isLog(HttpSession session) {
		return true;
	}

	/**
	 * @see SecurityServiceInterface {@link #logUser(Login)}
	 */
	@Override
	public Map<String, String> logUser(Login login) {
		return null;
	}
}