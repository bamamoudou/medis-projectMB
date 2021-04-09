package com.mediscreen.msclientui.configuration;

import com.mediscreen.msclientui.interfaces.MedicalRecordServiceInterface;
import com.mediscreen.msclientui.interfaces.MedicalReportServiceInterface;
import com.mediscreen.msclientui.interfaces.PatientServiceInterface;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.service.MedicalRecordService;
import com.mediscreen.msclientui.service.MedicalReportService;
import com.mediscreen.msclientui.service.PatientService;
import com.mediscreen.msclientui.service.SecurityService;
import com.mediscreen.msclientui.utils.ControllerUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public AppProperties applicationProperties(){
        return new AppProperties();
    }

    @Bean
    public ControllerUtils controllerUtils(){
        return new ControllerUtils();
    }

    @Bean
    public SecurityServiceInterface securityService(){
        return new SecurityService();
    }

    @Bean
    public MedicalRecordServiceInterface medicalRecordService(){
        return new MedicalRecordService(securityService());
    }

    @Bean
    public MedicalReportServiceInterface medicalReportService() {
        return new MedicalReportService(securityService());
    }

    @Bean
    public PatientServiceInterface patientService() {
        return new PatientService(securityService(), medicalRecordService(), medicalReportService());
    }
}
