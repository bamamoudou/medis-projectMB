package com.mediscreen.mspatientadmin.service;

import com.mediscreen.mspatientadmin.exception.EmptyDataException;
import com.mediscreen.mspatientadmin.exception.NotAllowedException;
import com.mediscreen.mspatientadmin.interfaces.SecurityServiceInterface;
import com.mediscreen.mspatientadmin.proxy.MSAuthenticationProxy;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SecurityService implements SecurityServiceInterface {
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
        if (StringUtils.isBlank(token)) throw new EmptyDataException("The authentication token is required");
        ResponseEntity<Void> validation = msAuthenticationProxy.validateToken(token);
        if (!validation.getStatusCode().equals(HttpStatus.OK)) throw new NotAllowedException("Permission denied");
    }
}
