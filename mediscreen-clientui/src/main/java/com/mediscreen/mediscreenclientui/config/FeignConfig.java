package com.mediscreen.mediscreenclientui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mediscreen.mediscreenclientui.exception.CustomErrorDecoder;

@Configuration
public class FeignConfig {
	@Bean
	public CustomErrorDecoder customErrorDecoder() {
		return new CustomErrorDecoder();
	}
}