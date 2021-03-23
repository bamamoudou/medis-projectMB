package com.mediscreen.mediscreenpatient.serviceImpl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mediscreen.mediscreenpatient.exception.EmptyDataException;
import com.mediscreen.mediscreenpatient.exception.NotAllowedException;
import com.mediscreen.mediscreenpatient.proxy.MSAuthenticationProxy;
import com.mediscreen.mediscreenpatient.service.SecurityService;

public class SecurityServiceImpl implements SecurityService {
	/**
	 * ms-authentication proxy
	 */
	@Autowired
	private MSAuthenticationProxy authenticationProxy;

	public SecurityServiceImpl() {
    }

	/**
	 * @see SecurityService {@link #authenticationCheck(String)}
	 */
	@Override
	public void authenticationCheck(String token) {
		if (StringUtils.isBlank(token))
			throw new EmptyDataException("The authentication token is required");
		ResponseEntity<Void> validation = authenticationProxy.validateToken(token);
		if (!validation.getStatusCode().equals(HttpStatus.OK))
			throw new NotAllowedException("Permission denied");
	}
}