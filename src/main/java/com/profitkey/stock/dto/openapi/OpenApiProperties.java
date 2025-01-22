package com.profitkey.stock.dto.openapi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "openapi")
@Getter
@Setter
public class OpenApiProperties {
	private String apiKey;
	private String stockPriceInfoUrl;
}