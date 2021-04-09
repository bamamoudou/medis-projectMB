package com.mediscreen.msauthentication.services;

import com.mediscreen.msauthentication.model.Jwt;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.UUID;

public interface JwtService {
    /**
     * Create JWT
     * @param id
     * @param subject
     * @param issuer
     * @param claims
     * @param ttlMillis
     * @return
     */
    Jwt createJWT(UUID id, String subject, String issuer, Map<String, Object> claims, long ttlMillis);

    /**
     * Parse JWT
     * @param token
     * @return
     */
    Claims parseJWT(String token);
}
