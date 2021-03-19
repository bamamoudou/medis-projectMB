package com.mediscreen.mediscreenpatient.model;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import com.mediscreen.mediscreenpatient.serviceImpl.CountryServiceImpl;
import com.mediscreen.mediscreenpatient.serviceImpl.HTTPRequestServiceImpl;

public class Country {
	@NotNull(message = "CODE is mandatory")
	private String code;
	private String wording;
	private CountryServiceImpl countryService;

	public Country() {
	}

	/**
	 * Constructor
	 * 
	 * @param code
	 * @throws IOException
	 */
	public Country(String code) throws IOException {
		this.countryService = new CountryServiceImpl(new HTTPRequestServiceImpl());
		this.setCountry(code);
	}

	/**
	 * Constructor
	 * 
	 * @param code
	 * @param countryService
	 * @throws IOException
	 */
	public Country(String code, CountryServiceImpl countryService) throws IOException {
		this.countryService = countryService;
		this.setCountry(code);
	}

	public String getCode() {
		return code;
	}

	public String getWording() {
		return wording;
	}

	public void setCountry(String code) throws IOException {
		this.code = code.toUpperCase();
		this.wording = countryService.getNameOfCountry(code.toUpperCase());
	}

}