package com.mediscreen.mediscreenclientui.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.mediscreen.mediscreenclientui.model.Login;

public interface SecurityService {
	/**
	 * Check authentication with request ms-authentication
	 * 
	 * @param token
	 * @return
	 */
	void authenticationCheck(String token);

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
	Map<String, String> logUser(Login login);
}