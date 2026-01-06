package com.user_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.user_management.Feign")
@SpringBootApplication
public class CapstoneUserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneUserManagementApplication.class, args);
	}
}
