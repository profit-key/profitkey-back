package com.profitkey.stock.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "korea-investment-api")
@Getter
@Setter
public class KisApiProperties {
	private String apiKey;
	private String secretKey;
	private String devApiUrl;
	private String oauth2TokenUrl;
	private String inquirePriceUrl;
}