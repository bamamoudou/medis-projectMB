package com.mediscreen.mspatientadmin.configuration;

import com.mediscreen.mspatientadmin.dao.PatientDaoImpl;
import com.mediscreen.mspatientadmin.service.PatientService;
import com.mediscreen.mspatientadmin.service.SecurityService;
import com.mediscreen.mspatientadmin.dao.PatientDao;
import com.mediscreen.mspatientadmin.serviceImpl.PatientServiceImpl;
import com.mediscreen.mspatientadmin.serviceImpl.SecurityServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
	@Bean
	public AppProperties applicationProperties() {
		return new AppProperties();
	}

	@Bean
	public DatabaseConfigurationInterface databaseConfiguration() {
		return new DatabaseConfiguration(applicationProperties());
	}

	@Bean
	public PatientDao patientDao() {
		return new PatientDaoImpl(databaseConfiguration());
	}

	@Bean
	public PatientService patientService() {
		return new PatientServiceImpl(patientDao());
	}

	@Bean
	public SecurityService securityService() {
		return new SecurityServiceImpl();
	}
}