package com.profitkey.stock.config.environment;

import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EnvironmentChecker {
	public final Environment environment;

	public EnvironmentChecker(Environment environment) {
		this.environment = environment;
	}

	@PostConstruct
	public void init() {
		String url = environment.getProperty("spring.profiles.url");
		String name = environment.getProperty("spring.config.activate.on-profile");

		log.info("url = {}", url);
		log.info("name = {}", name);
	}
}
