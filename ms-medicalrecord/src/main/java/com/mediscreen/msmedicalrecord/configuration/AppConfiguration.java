package com.mediscreen.msmedicalrecord.configuration;

import com.mediscreen.msmedicalrecord.dao.MedicalRecordDao;
import com.mediscreen.msmedicalrecord.interfaces.DatabaseConfigurationInterface;
import com.mediscreen.msmedicalrecord.interfaces.MedicalRecordDaoInterface;
import com.mediscreen.msmedicalrecord.interfaces.MedicalRecordServiceInterface;
import com.mediscreen.msmedicalrecord.interfaces.SecurityServiceInterface;
import com.mediscreen.msmedicalrecord.service.MedicalRecordService;
import com.mediscreen.msmedicalrecord.service.SecurityService;
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
    public DatabaseConfigurationInterface databaseConfiguration(){
        return new DatabaseConfiguration(appProperties());
    }

    @Bean
    public MedicalRecordDaoInterface medicalRecordDao(){
        return new MedicalRecordDao(databaseConfiguration());
    }

    @Bean
    public MedicalRecordServiceInterface medicalRecordService(){
        return new MedicalRecordService(medicalRecordDao());
    }
}
