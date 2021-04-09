package com.mediscreen.mspatientadmin.serviceImpl;

import com.mediscreen.mspatientadmin.exception.EmptyDataException;
import com.mediscreen.mspatientadmin.exception.NotAllowedException;
import com.mediscreen.mspatientadmin.proxy.MSAuthenticationProxy;
import com.mediscreen.mspatientadmin.service.SecurityService;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SecurityServiceImpl implements SecurityService {
	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * ms-authentication proxy
	 */
	@Autowired
	private MSAuthenticationProxy msAuthenticationProxy;

	/**
	 * Constructor
	 */
	public SecurityServiceImpl() {
	}

	/**
	 * Constructor
	 * 
	 * @param msAuthenticationProxy
	 */
	public SecurityServiceImpl(MSAuthenticationProxy msAuthenticationProxy) {
		this.msAuthenticationProxy = msAuthenticationProxy;
	}

	/**
	 * @see SecurityService {@link #authenticationCheck(String)}
	 */
	@Override
	public void authenticationCheck(String token) {
		if (StringUtils.isBlank(token)) {
			logger.error("Error : The authentication token is required");
			throw new EmptyDataException("The authentication token is required");
		}
		ResponseEntity<Void> validation = msAuthenticationProxy.validateToken(token);
		if (!validation.getStatusCode().equals(HttpStatus.OK)) {
			logger.error("Permission denied : " + token);
			throw new NotAllowedException("Permission denied");
		}
	}
}