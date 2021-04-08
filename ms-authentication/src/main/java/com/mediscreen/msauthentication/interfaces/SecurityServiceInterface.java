package com.mediscreen.msauthentication.interfaces;

import com.mediscreen.msauthentication.models.Jwt;
import com.mediscreen.msauthentication.models.Login;

public interface SecurityServiceInterface {
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
