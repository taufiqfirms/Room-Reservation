package com.kelompokdua.booking;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
		name = "enigmaAuth",
		description = "JWT auth description",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in =  SecuritySchemeIn.HEADER
)

public class NigmaBookingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(NigmaBookingManagementApplication.class, args);
	}

}
