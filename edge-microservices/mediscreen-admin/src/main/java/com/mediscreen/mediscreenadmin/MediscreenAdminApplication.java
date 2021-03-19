package com.mediscreen.mediscreenadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class MediscreenAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediscreenAdminApplication.class, args);
	}

}
