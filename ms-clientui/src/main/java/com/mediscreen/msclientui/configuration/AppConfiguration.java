package com.mediscreen.msclientui.configuration;

import com.mediscreen.msclientui.service.MedicalRecordService;
import com.mediscreen.msclientui.service.MedicalReportService;
import com.mediscreen.msclientui.service.PatientService;
import com.mediscreen.msclientui.service.SecurityService;
import com.mediscreen.msclientui.serviceImpl.MedicalRecordServiceImpl;
import com.mediscreen.msclientui.serviceImpl.MedicalReportServiceImpl;
import com.mediscreen.msclientui.serviceImpl.PatientServiceImpl;
import com.mediscreen.msclientui.serviceImpl.SecurityServiceImpl;
import com.mediscreen.msclientui.utils.ControllerUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
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
	public MedicalRecordService medicalRecordService() {
		return new MedicalRecordServiceImpl(securityService());
	}

	@Bean
	public MedicalReportService medicalReportService() {
		return new MedicalReportServiceImpl(securityService());
	}

	@Bean
	public PatientService patientService() {
		return new PatientServiceImpl(securityService(), medicalRecordService(), medicalReportService());
	}
}