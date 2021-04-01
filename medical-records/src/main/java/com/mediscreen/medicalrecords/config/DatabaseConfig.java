package com.mediscreen.medicalrecords.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseConfig implements DatabaseConfigurationInterface {
	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * Database host
	 */
	private String host;

	/**
	 * Database port
	 */
	private String port;

	/**
	 * Database database name
	 */
	private String database;

	/**
	 * Database username
	 */
	private String user;

	/**
	 * Database password
	 */
	private String password;

	/**
	 * Application properties
	 */
	private AppProperties appProperties;

	/**
	 * Constructor
	 * 
	 * @param appProperties
	 */
	public DatabaseConfig(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

}
