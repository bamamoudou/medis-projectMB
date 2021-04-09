package com.mediscreen.msmedicalreport.service;

import com.mediscreen.msmedicalreport.exception.EmptyDataException;
import com.mediscreen.msmedicalreport.exception.NotAllowedException;
import com.mediscreen.msmedicalreport.interfaces.SecurityServiceInterface;
import com.mediscreen.msmedicalreport.proxy.MSZuulProxy;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SecurityService implements SecurityServiceInterface {
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
    public SecurityService() {
    }

    /**
     * Constructor
     * @param msZuulProxy
     */
    public SecurityService(MSZuulProxy msZuulProxy) {
        this.msZuulProxy = msZuulProxy;
    }

    /**
     * @see SecurityServiceInterface {@link #authenticationCheck(String)}
     */
    @Override
    public void authenticationCheck(String token) {
        if (StringUtils.isBlank(token)){
            logger.error("Error : The authentication token is required");
            throw new EmptyDataException("The authentication token is required");
        }
        ResponseEntity<Void> validation = msZuulProxy.msAuthentication_validateToken(token);
        if (!validation.getStatusCode().equals(HttpStatus.OK)){
            logger.error("Permission denied : " + token);
            throw new NotAllowedException("Permission denied");
        }
    }
}
