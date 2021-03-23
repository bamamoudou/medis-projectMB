package com.mediscreen.mediscreenpatient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mediscreen.mediscreenpatient.exception.CustomErrorDecoder;

@Configuration
public class FeignConfig {
	@Bean
	public CustomErrorDecoder customErrorDecoder() {
		return new CustomErrorDecoder();
	}
}