package com.mediscreen.mspatientadmin.service;

import com.mediscreen.mspatientadmin.exception.EmptyDataException;
import com.mediscreen.mspatientadmin.exception.NotAllowedException;
import com.mediscreen.mspatientadmin.interfaces.SecurityServiceInterface;
import com.mediscreen.mspatientadmin.proxy.MSAuthenticationProxy;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SecurityService implements SecurityServiceInterface {
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
    public SecurityService() {
    }

    /**
     * Constructor
     * @param msAuthenticationProxy
     */
    public SecurityService(MSAuthenticationProxy msAuthenticationProxy) {
        this.msAuthenticationProxy = msAuthenticationProxy;
    }

    /**
     * @see SecurityServiceInterface {@link #authenticationCheck(String)}
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
