package com.mediscreen.medicalrecords.serviceImpl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mediscreen.medicalrecords.exception.EmptyDataException;
import com.mediscreen.medicalrecords.exception.NotAllowedException;
import com.mediscreen.medicalrecords.proxy.MSAuthenticationProxy;
import com.mediscreen.medicalrecords.service.SecurityService;

public class SecurityServiceImpl implements SecurityService {
	/**
	 * ms-authentication proxy
	 */
	@Autowired
	private MSAuthenticationProxy msAuthenticationProxy;

	public SecurityServiceImpl() {
    }

	/**
	 * @see SecurityServiceInterface {@link #authenticationCheck(String)}
	 */
	@Override
	public void authenticationCheck(String token) {
		if (StringUtils.isBlank(token))
			throw new EmptyDataException("The authentication token is required");
		ResponseEntity<Void> validation = msAuthenticationProxy.validateToken(token);
		if (!validation.getStatusCode().equals(HttpStatus.OK))
			throw new NotAllowedException("Permission denied");
	}
}
