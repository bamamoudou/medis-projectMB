package com.mediscreen.mediscreenauthentification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mediscreen.mediscreenauthentification.service.JwtService;
import com.mediscreen.mediscreenauthentification.service.SecurityService;
import com.mediscreen.mediscreenauthentification.serviceImpl.JwtServiceImpl;
import com.mediscreen.mediscreenauthentification.serviceImpl.SecurityServiceImpl;

@Configuration
public class AppConfig {
	@Bean
	public AppProperties applicationProperties() {
		return new AppProperties();
	}

	@Bean
	public JwtService tokenService() {
		return new JwtServiceImpl(applicationProperties());
	}

	@Bean
	public SecurityService serviceInterface() {
		return new SecurityServiceImpl(tokenService(), new BCryptPasswordEncoder(), applicationProperties());
	}
}