package com.mediscreen.msauthentication.services;

import com.mediscreen.msauthentication.model.Jwt;
import com.mediscreen.msauthentication.model.Login;

public interface SecurityService {
    /**
     * Log user
     * @param login
     * @return
     */
    Jwt logUser(Login login);

    /**
     * Check if user is logging
     * @param token
     * @return
     */
    boolean isLog(String token);
}
