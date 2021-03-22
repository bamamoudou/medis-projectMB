package com.mediscreen.mediscreenpatient.service;

import java.io.IOException;
import java.util.Map;

public interface CountryService {
	/**
	 * GET complete name of a country by API request
	 * 
	 * @param code
	 * @return
	 * @throws IOException
	 */
	String getNameOfCountry(String code) throws IOException;

	/**
	 * GET all name and alpha code of countries by API request
	 * 
	 * @return
	 * @throws IOException
	 */
	Map<String, String> getAllCountries() throws IOException;
}