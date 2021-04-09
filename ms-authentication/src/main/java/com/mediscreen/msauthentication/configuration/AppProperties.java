package com.mediscreen.msauthentication.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mediscreen-ms-authentication")
public class AppProperties {
    private String username;
    private String password;
    private String jwtSecret;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }
}