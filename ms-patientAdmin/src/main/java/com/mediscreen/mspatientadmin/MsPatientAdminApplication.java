package com.mediscreen.mspatientadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients("com.mediscreen.mspatientadmin")
public class MsPatientAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsPatientAdminApplication.class, args);
	}
}
