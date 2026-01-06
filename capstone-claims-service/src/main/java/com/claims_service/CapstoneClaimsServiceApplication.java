package com.claims_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.claims_service.Feign")
public class CapstoneClaimsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneClaimsServiceApplication.class, args);
	}

}
