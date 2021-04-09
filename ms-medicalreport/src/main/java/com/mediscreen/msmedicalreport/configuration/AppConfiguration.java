package com.mediscreen.msmedicalreport.configuration;

import com.mediscreen.msmedicalreport.service.MedicalReportService;
import com.mediscreen.msmedicalreport.service.SecurityService;
import com.mediscreen.msmedicalreport.serviceImpl.MedicalReportServiceImpl;
import com.mediscreen.msmedicalreport.serviceImpl.SecurityServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	public MedicalReportService medicalReportService() {
		return new MedicalReportServiceImpl();
	}
}