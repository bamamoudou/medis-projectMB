package com.mediscreen.mspatientadmin.configuration;

import com.mediscreen.mspatientadmin.dao.PatientDao;
import com.mediscreen.mspatientadmin.interfaces.DatabaseConfigurationInterface;
import com.mediscreen.mspatientadmin.interfaces.PatientDaoInterface;
import com.mediscreen.mspatientadmin.interfaces.PatientServiceInterface;
import com.mediscreen.mspatientadmin.interfaces.SecurityServiceInterface;
import com.mediscreen.mspatientadmin.service.PatientService;
import com.mediscreen.mspatientadmin.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public AppProperties applicationProperties(){
        return new AppProperties();
    }

    @Bean
    public DatabaseConfigurationInterface databaseConfiguration() {
        return new DatabaseConfiguration(applicationProperties());
    }

    @Bean
    public PatientDaoInterface patientDao(){
        return new PatientDao(databaseConfiguration());
    }

    @Bean
    public PatientServiceInterface patientService() {
        return new PatientService(patientDao());
    }

    @Bean
    public SecurityServiceInterface securityService() {
        return new SecurityService();
    }
}
