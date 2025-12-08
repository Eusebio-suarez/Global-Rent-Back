package com.global.GobalRent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class GobalRentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GobalRentApplication.class, args);
	}

}
