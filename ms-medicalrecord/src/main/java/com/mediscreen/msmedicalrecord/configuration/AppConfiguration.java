package com.mediscreen.msmedicalrecord.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mediscreen.msmedicalrecord.dao.MedicalRecordDaoImpl;
import com.mediscreen.msmedicalrecord.service.MedicalRecordService;
import com.mediscreen.msmedicalrecord.service.SecurityService;
import com.mediscreen.msmedicalrecord.dao.MedicalRecordDao;
import com.mediscreen.msmedicalrecord.serviceImpl.MedicalRecordServiceImpl;
import com.mediscreen.msmedicalrecord.serviceImpl.SecurityServiceImpl;

@Configuration
public class AppConfiguration {
	@Bean
	public AppProperties appProperties() {
		return new AppProperties();
	}

	@Bean
	public SecurityService securityService() {
		return new SecurityServiceImpl();
	}

	@Bean
	public DatabaseConfigurationInterface databaseConfiguration() {
		return new DatabaseConfiguration(appProperties());
	}

	@Bean
	public MedicalRecordDao medicalRecordDao() {
		return new MedicalRecordDaoImpl(databaseConfiguration());
	}

	@Bean
	public MedicalRecordService medicalRecordService() {
		return new MedicalRecordServiceImpl(medicalRecordDao());
	}
}