package com.mediscreen.mediscreenclientui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mediscreen-mediscreen-clientui")
public class AppProperties {
	private String defaultUrl;

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
}