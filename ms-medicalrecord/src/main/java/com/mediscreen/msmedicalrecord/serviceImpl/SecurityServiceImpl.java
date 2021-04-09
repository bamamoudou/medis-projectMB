package com.mediscreen.msmedicalrecord.serviceImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mediscreen.msmedicalrecord.exception.EmptyDataException;
import com.mediscreen.msmedicalrecord.exception.NotAllowedException;
import com.mediscreen.msmedicalrecord.proxy.MSZuulProxy;
import com.mediscreen.msmedicalrecord.service.SecurityService;

public class SecurityServiceImpl implements SecurityService {
	/**
	 * Logger
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * ems-zuul proxy
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
	public void authenticationCheck(String token) {
		if (StringUtils.isBlank(token)) {
			logger.error("Error : The authentication token is required");
			throw new EmptyDataException("The authentication token is required");
		}
		ResponseEntity<Void> validation = msZuulProxy.msAuthenticationValidateToken(token);
		if (!validation.getStatusCode().equals(HttpStatus.OK)) {
			logger.error("Permission denied");
			throw new NotAllowedException("Permission denied : " + token);
		}
	}
}