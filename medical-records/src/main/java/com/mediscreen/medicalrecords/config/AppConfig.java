package com.mediscreen.medicalrecords.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mediscreen.medicalrecords.dao.MedicalRecordDao;
import com.mediscreen.medicalrecords.daoImpl.MedicalRecordDaoImpl;
import com.mediscreen.medicalrecords.service.MedicalRecordService;
import com.mediscreen.medicalrecords.service.SecurityService;

@Configuration
public class AppConfig {
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
		return new DatabaseConfig(appProperties());
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
