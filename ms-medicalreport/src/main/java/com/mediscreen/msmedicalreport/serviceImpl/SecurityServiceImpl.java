package com.mediscreen.msmedicalreport.serviceImpl;

import com.mediscreen.msmedicalreport.exception.EmptyDataException;
import com.mediscreen.msmedicalreport.exception.NotAllowedException;
import com.mediscreen.msmedicalreport.proxy.MSZuulProxy;
import com.mediscreen.msmedicalreport.service.SecurityService;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SecurityServiceImpl implements SecurityService {
	/**
	 * Logger4j2
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
			logger.error("Permission denied : " + token);
			throw new NotAllowedException("Permission denied");
		}
	}
}