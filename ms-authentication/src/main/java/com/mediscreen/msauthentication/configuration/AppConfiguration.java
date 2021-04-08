package com.mediscreen.msauthentication.configuration;

import com.mediscreen.msauthentication.interfaces.SecurityServiceInterface;
import com.mediscreen.msauthentication.interfaces.JwtServiceInterface;
import com.mediscreen.msauthentication.service.SecurityService;
import com.mediscreen.msauthentication.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfiguration {
    @Bean
    public AppProperties applicationProperties(){
        return new AppProperties();
    }

    @Bean
    public JwtServiceInterface tokenService(){
        return new JwtService(applicationProperties());
    }

    @Bean
    public SecurityServiceInterface serviceInterface(){
        return new SecurityService(tokenService(), new BCryptPasswordEncoder(), applicationProperties());
    }
}
