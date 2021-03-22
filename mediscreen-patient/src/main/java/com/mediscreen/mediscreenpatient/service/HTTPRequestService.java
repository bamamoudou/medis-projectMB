package com.mediscreen.mediscreenpatient.service;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public interface HTTPRequestService {
	/**
	 * API reader, HTTP GET request
	 * 
	 * @param url
	 * @param urlParams
	 * @return Content of API GET request
	 * @throws IOException
	 * @throws JSONException
	 */
	JSONObject getReq(String url, Map<String, String> urlParams) throws IOException, JSONException;

	/**
	 * API POST request with a form
	 * 
	 * @param url
	 * @param formParams
	 * @return JSON API response
	 * @throws IOException
	 * @throws JSONException
	 */
	JSONObject postFormReq(String url, Map<String, String> formParams) throws IOException, JSONException;
}