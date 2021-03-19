package com.mediscreen.mediscreenpatient.serviceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mediscreen.mediscreenpatient.service.CountryService;
import com.mediscreen.mediscreenpatient.service.HTTPRequestService;
import com.mediscreen.mediscreenpatient.util.MSArrayUtil;

@Singleton
public class CountryServiceImpl implements CountryService {
	/**
	 * Logger log4j2
	 */
	private static final Logger logger = LogManager.getLogger("CountryService");

	private HTTPRequestService httpRequestService;

	/**
	 * Constructor
	 * 
	 * @param httpRequestService
	 */
	public CountryServiceImpl(HTTPRequestService httpRequestService) {
		this.httpRequestService = httpRequestService;
	}

	/**
	 * @see CountryServiceInterface {@link #getNameOfCountry(String)}
	 */
	@Override
	public String getNameOfCountry(String code) throws IOException {
		String name = null;
		Map<String, String> params = new HashMap<>();
		params.put("fields", "name");
		JSONObject data = httpRequestService.getReq("https://restcountries.eu/rest/v2/alpha/" + code, params);
		Integer status = data.getInt("status");
		if (status < 299) {
			JSONObject content = (JSONObject) data.get("content");
			name = content.getString("name");
			logger.info("API request : CountryService -> " + code);
		}
		return name;
	}

	/**
	 * @see CountryServiceInterface {@link #getAllCountries()}
	 */
	@Override
	public Map<String, String> getAllCountries() throws IOException {
		Map<String, String> countries = null;
		JSONObject data = httpRequestService.getReq("https://restcountries.eu/rest/v2/all?fields=name;alpha3Code;",
				null);
		Integer status = data.getInt("status");
		if (status < 299) {
			countries = new HashMap<>();
			JSONArray content = (JSONArray) data.get("content");
			for (int i = 0; i < content.length(); i++) {
				JSONObject country = (JSONObject) content.get(i);
				countries.put(country.getString("alpha3Code"), country.getString("name"));
			}
			countries = MSArrayUtil.sortMapByValue(countries);
			logger.info("API request : CountryService -> All");
		}
		return countries;
	}
}