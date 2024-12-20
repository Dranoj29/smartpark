package com.dranoj.SmartPark;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmartParkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartParkApplication.class, args);
	}

	//Swagger
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info()
						.title("Smart Park Webservice")
						.version("v1")
				);
	}
}
