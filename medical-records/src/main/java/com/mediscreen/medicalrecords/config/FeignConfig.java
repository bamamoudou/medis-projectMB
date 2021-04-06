package com.mediscreen.medicalrecords.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mediscreen.medicalrecords.exception.CustomErrorDecoder;

@Configuration
public class FeignConfig {
	@Bean
	public CustomErrorDecoder customErrorDecoder() {
		return new CustomErrorDecoder();
	}
}