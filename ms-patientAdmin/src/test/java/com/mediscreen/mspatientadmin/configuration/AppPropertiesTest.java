package com.mediscreen.mspatientadmin.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppPropertiesTest {
	private AppProperties appProperties;

	@BeforeEach
	void init_test() {
		this.appProperties = new AppProperties();
	}

	@Tag("AppPropertiesTest")
	@Test
	void productionDB_test() {
		appProperties.setHost("localhost");
		appProperties.setPort(1000);
		appProperties.setDatabase("DB");
		appProperties.setUser("user");
		appProperties.setPassword("password");

		assertThat(appProperties.getHost()).isEqualTo("localhost");
		assertThat(appProperties.getPort()).isEqualTo(1000);
		assertThat(appProperties.getDatabase()).isEqualTo("DB");
		assertThat(appProperties.getUser()).isEqualTo("user");
		assertThat(appProperties.getPassword()).isEqualTo("password");
	}
}