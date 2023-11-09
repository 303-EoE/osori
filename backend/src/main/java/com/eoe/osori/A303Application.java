package com.eoe.osori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class A303Application {

	public static void main(String[] args) {
		SpringApplication.run(A303Application.class, args);
	}

}
