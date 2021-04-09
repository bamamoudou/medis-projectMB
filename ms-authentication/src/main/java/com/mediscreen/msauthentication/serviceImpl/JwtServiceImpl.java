package com.mediscreen.msauthentication.serviceImpl;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mediscreen.msauthentication.configuration.AppProperties;
import com.mediscreen.msauthentication.exception.NotAllowedException;
import com.mediscreen.msauthentication.model.Jwt;
import com.mediscreen.msauthentication.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtServiceImpl implements JwtService {
	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * Application properties
	 */
	private AppProperties appProperties;

	/**
	 * Constructor
	 * 
	 * @param appProperties
	 */
	public JwtServiceImpl(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	/**
	 * @see JwtService {@link #createJWT(UUID, String, String, Map, long)}
	 */
	@Override
	public Jwt createJWT(UUID id, String subject, String issuer, Map<String, Object> claims, long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		Date now = new Date(System.currentTimeMillis());
		Date expirationDate;

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(appProperties.getJwtSecret());
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		JwtBuilder builder = Jwts.builder().setId(id.toString()).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
				.setClaims(claims).signWith(signatureAlgorithm, signingKey);

		expirationDate = new Date(System.currentTimeMillis() + ttlMillis * 1000);
		builder.setExpiration(expirationDate);

		return new Jwt(builder.compact(), LocalDateTime.now(),
				Instant.ofEpochMilli(expirationDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
	}

	/**
	 * @see JwtService {@link #parseJWT(String)}
	 */
	@Override
	public Claims parseJWT(String token) {
		Claims claims = null;

		try {
			claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(appProperties.getJwtSecret()))
					.parseClaimsJws(token).getBody();
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature.");
			throw new NotAllowedException("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token.");
			throw new NotAllowedException("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token.");
			throw new NotAllowedException("Expired JWT token.");
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT token.");
			throw new NotAllowedException("Unsupported JWT token.");
		} catch (IllegalArgumentException e) {
			logger.error("JWT token compact of handler are invalid.");
			throw new NotAllowedException("JWT token compact of handler are invalid.");
		}

		return claims;
	}
}