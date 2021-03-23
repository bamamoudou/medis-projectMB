package com.mediscreen.mediscreenclientui.service;

import javax.servlet.http.HttpSession;

import com.mediscreen.mediscreenclientui.model.Login;

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
	String logUser(Login login, HttpSession session);
}