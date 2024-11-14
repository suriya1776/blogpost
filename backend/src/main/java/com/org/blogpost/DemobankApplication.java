package com.org.blogpost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemobankApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemobankApplication.class, args);
		System.out.println("Demo bank");
	}

}
