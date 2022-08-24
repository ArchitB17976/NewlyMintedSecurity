package com.locker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component("com.locker.jwt")
public class LockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LockerApplication.class, args);
	}

}
