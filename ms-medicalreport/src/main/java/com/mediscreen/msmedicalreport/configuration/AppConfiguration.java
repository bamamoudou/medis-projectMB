package com.mediscreen.msmedicalreport.configuration;

import com.mediscreen.msmedicalreport.interfaces.MedicalReportServiceInterface;
import com.mediscreen.msmedicalreport.interfaces.SecurityServiceInterface;
import com.mediscreen.msmedicalreport.service.MedicalReportService;
import com.mediscreen.msmedicalreport.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public AppProperties appProperties(){
        return new AppProperties();
    }

    @Bean
    public SecurityServiceInterface securityService() {
        return new SecurityService();
    }

    @Bean
    public MedicalReportServiceInterface medicalReportService(){
        return new MedicalReportService();
    }
}
