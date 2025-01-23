package com.profitkey.stock.config.environment.config;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EnvironmentChecker {
	private final Environment environment;

	private EnvironmentChecker(Environment environment) {
		this.environment = environment;
	}

	@PostConstruct
	public void init() {
		String url = environment.getProperty("spring.profiles.url");
		String name = environment.getProperty("spring.config.activate.on-profile");

		log.info("url = {}", url);
		log.info("name = {}", name);
	}

	public OpenAPI getSwaggerInfoByEnv() {
		String env = environment.getProperty("spring.config.activate.on-profile");
		String server_url = "";
		log.info("env = {}", env);
		switch (env) {
			case "dev", "prod":
				server_url = environment.getProperty("spring.profiles.url");
				break;
			default:
				server_url = "http://localhost:8080";
				break;
		}
		return new OpenAPI().info(new Info().title("Stock API Documentation")
			.description("Stock service API documentation")
			.version("v1.0.0")).servers(List.of(new Server().url(server_url)  // HTTPS URL 설정
		));
	}

}
