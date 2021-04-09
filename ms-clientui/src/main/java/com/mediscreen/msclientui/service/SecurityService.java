package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.model.Login;

import javax.servlet.http.HttpSession;

public interface SecurityService {
	/**
	 * Check authentication with request ms-authentication
	 * 
	 * @param token
	 * @return
	 */
	boolean authenticationCheck(String token);

	/**
	 * Check if user is log, read token and validate it
	 * 
	 * @param session
	 * @return
	 */
	boolean isLog(HttpSession session);

	/**
	 * Log user and generate token
	 * 
	 * @param login
	 * @return
	 */
	String logUser(Login login);
}