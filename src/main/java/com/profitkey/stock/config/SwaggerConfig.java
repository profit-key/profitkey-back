package com.profitkey.stock.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI swaggerApi() {
        return null;
    }

    @Bean
    public GroupedOpenApi customOpenApi() {
        // 스웨거 범위 지정
        return GroupedOpenApi.builder()
            .group("profitkey")
            .packagesToScan("com.profitkey.stock")
            .build();
    }
}
