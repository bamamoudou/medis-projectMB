package com.mediscreen.msclientui.configuration;

import com.mediscreen.msclientui.exception.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
	@Bean
	public CustomErrorDecoder customErrorDecoder() {
		return new CustomErrorDecoder();
	}
}