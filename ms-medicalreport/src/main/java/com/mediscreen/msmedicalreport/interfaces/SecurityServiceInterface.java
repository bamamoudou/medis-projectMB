package com.mediscreen.msmedicalreport.interfaces;

public interface SecurityServiceInterface {
    /**
     * Check authentication with request ms-authentication
     * @param token
     * @return
     */
    void authenticationCheck(String token);
}
