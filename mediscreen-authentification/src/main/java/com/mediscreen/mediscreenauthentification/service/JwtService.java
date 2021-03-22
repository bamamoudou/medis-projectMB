package com.mediscreen.mediscreenauthentification.service;

import java.util.Map;
import java.util.UUID;

import com.mediscreen.mediscreenauthentification.model.Jwt;

import io.jsonwebtoken.Claims;

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