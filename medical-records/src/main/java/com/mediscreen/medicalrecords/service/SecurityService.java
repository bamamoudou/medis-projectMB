package com.mediscreen.medicalrecords.service;

public interface SecurityService {
	/**
	 * Check authentication with request ms-authentication
	 * 
	 * @param token
	 * @return
	 */
	void authenticationCheck(String token);
}