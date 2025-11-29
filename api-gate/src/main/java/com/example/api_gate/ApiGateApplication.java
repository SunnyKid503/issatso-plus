package com.example.api_gate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.api_gate", "Security"})

public class ApiGateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGateApplication.class, args);
	}

}
