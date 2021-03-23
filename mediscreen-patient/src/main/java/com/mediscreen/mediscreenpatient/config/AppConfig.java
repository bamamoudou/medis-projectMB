package com.mediscreen.mediscreenpatient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mediscreen.mediscreenpatient.dao.PatientDAO;
import com.mediscreen.mediscreenpatient.daoImpl.PatientDAOImpl;
import com.mediscreen.mediscreenpatient.service.CountryService;
import com.mediscreen.mediscreenpatient.service.HTTPRequestService;
import com.mediscreen.mediscreenpatient.service.PatientService;
import com.mediscreen.mediscreenpatient.service.SecurityService;
import com.mediscreen.mediscreenpatient.serviceImpl.CountryServiceImpl;
import com.mediscreen.mediscreenpatient.serviceImpl.HTTPRequestServiceImpl;
import com.mediscreen.mediscreenpatient.serviceImpl.PatientServiceImpl;
import com.mediscreen.mediscreenpatient.serviceImpl.SecurityServiceImpl;

@Configuration
public class AppConfig {
	@Bean
	public AppProperties applicationProperties() {
		return new AppProperties();
	}

	@Bean
	public DatabaseConfigInterface databaseConfiguration() {
		return new DatabaseConfig(applicationProperties());
	}

	@Bean
	public HTTPRequestService httpRequestService() {
		return new HTTPRequestServiceImpl();
	}

	@Bean
	public CountryService countryService() {
		return new CountryServiceImpl(httpRequestService());
	}

	@Bean
	public PatientDAO patientDao() {
		return new PatientDAOImpl(databaseConfiguration());
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