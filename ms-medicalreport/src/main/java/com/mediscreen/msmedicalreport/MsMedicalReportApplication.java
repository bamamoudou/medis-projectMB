package com.mediscreen.msmedicalreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients("com.mediscreen.msmedicalreport")
public class MsMedicalReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsMedicalReportApplication.class, args);
    }
}
