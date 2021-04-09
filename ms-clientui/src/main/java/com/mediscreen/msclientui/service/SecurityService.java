package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.exception.EmptyDataException;
import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.model.Jwt;
import com.mediscreen.msclientui.model.Login;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public class SecurityService implements SecurityServiceInterface {
    /**
     * Logger log4j2
     */
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Zuul proxy
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
    public boolean authenticationCheck(String token) {
        if (StringUtils.isBlank(token)) {
            logger.error("The authentication token is required");
            throw new EmptyDataException("The authentication token is required");
        }
        ResponseEntity<Void> validation = msZuulProxy.msAuthentication_validateToken(token);
        if (!validation.getStatusCode().equals(HttpStatus.OK)) {
            logger.error("Permission denied");
            throw new NotAllowedException("Permission denied");
        }
        return true;
    }

    /**
     * @see SecurityServiceInterface {@link #isLog(HttpSession)}
     */
    @Override
    public boolean isLog(HttpSession session){
        String token = (String) session.getAttribute("token");
        if(token != null && !StringUtils.isBlank(token)) return authenticationCheck(token);
        return false;
    }

    /**
     * @see SecurityServiceInterface {@link #logUser(Login)}
     */
    @Override
    public String logUser(Login login){
        if (!StringUtils.isBlank(login.getUsername()) && !StringUtils.isBlank(login.getPassword())) {
            try {
                ResponseEntity<Jwt> jwt = msZuulProxy.msAuthentication_generateToken(login);
                if (jwt != null && jwt.getStatusCode().equals(HttpStatus.OK) && jwt.getBody() != null && jwt.getBody().getToken() != null) {
                    return jwt.getBody().getToken();
                } else {
                    logger.error("JWT no generated");
                    throw new NullPointerException("JWT no generated");
                }
            } catch (NotAllowedException e) {
                logger.error("Permission denied, username or password are incorrect");
                throw new NotAllowedException("Permission denied, username or password are incorrect");
            }
        } else {
            logger.error("Username and password are required");
            throw new EmptyDataException("Username and password are required");
        }
    }
}
