package com.mediscreen.msauthentication.configuration;

import com.mediscreen.msauthentication.serviceImpl.JwtServiceImpl;
import com.mediscreen.msauthentication.serviceImpl.SecurityServiceImpl;
import com.mediscreen.msauthentication.services.JwtService;
import com.mediscreen.msauthentication.services.SecurityService;

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
    public JwtServiceImpl tokenService(){
        return new JwtServiceImpl(applicationProperties());
    }

    @Bean
    public SecurityServiceImpl serviceInterface(){
        return new SecurityServiceImpl(tokenService(), new BCryptPasswordEncoder(), applicationProperties());
    }
}
