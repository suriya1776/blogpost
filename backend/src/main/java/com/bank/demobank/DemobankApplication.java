package com.bank.demobank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemobankApplication {

	public static void main(String[] args) {
		System.out.println("Inside main functio");
		SpringApplication.run(DemobankApplication.class, args);
	}

}
