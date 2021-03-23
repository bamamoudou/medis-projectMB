package com.mediscreen.mediscreenclientui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mediscreen.mediscreenclientui.service.PatientService;
import com.mediscreen.mediscreenclientui.service.SecurityService;
import com.mediscreen.mediscreenclientui.serviceImpl.PatientServiceImpl;
import com.mediscreen.mediscreenclientui.serviceImpl.SecurityServiceImpl;
import com.mediscreen.mediscreenclientui.utils.ControllerUtils;

@Configuration
public class AppConfig {
	@Bean
	public AppProperties applicationProperties() {
		return new AppProperties();
	}

	@Bean
	public ControllerUtils controllerUtils() {
		return new ControllerUtils();
	}

	@Bean
	public SecurityService securityService() {
		return new SecurityServiceImpl();
	}

	@Bean
	public PatientService patientService() {
		return new PatientServiceImpl();
	}
}
