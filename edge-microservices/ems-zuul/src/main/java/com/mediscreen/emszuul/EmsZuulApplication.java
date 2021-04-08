package com.mediscreen.emszuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class EmsZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmsZuulApplication.class, args);
	}

}
