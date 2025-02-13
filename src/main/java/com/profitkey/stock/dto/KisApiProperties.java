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
	private String inquireDailyUrl;
	private String volumeRankUrl;
	private String marketCapUrl;
	private String investOpinionUrl;
	private String incomeStatementUrl;
	private String financialRatioUrl;
	private String profitRatioUrl;
	private String stabilityRatioUrl;
	private String growthRatioUrl;
	private String dividendUrl;
}