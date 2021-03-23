package com.mediscreen.mediscreenpatient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients("com.mediscreen.mediscreenpatient")
public class MediscreenPatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediscreenPatientApplication.class, args);
	}

}
