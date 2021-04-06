package com.mediscreen.medicalrecords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients("com.mediscreen.medicalrecords")
public class MedicalRecordsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalRecordsApplication.class, args);
	}

}
