package com.mediscreen.mediscreenauthentification.service;

import com.mediscreen.mediscreenauthentification.model.Jwt;
import com.mediscreen.mediscreenauthentification.model.Login;

public interface SecurityService {
	/**
	 * Log user
	 * 
	 * @param userLogin
	 * @return
	 */
	Jwt logUser(Login userLogin);

	/**
	 * Check if user is logging
	 * 
	 * @param token
	 * @return
	 */
	boolean isLog(String token);
}