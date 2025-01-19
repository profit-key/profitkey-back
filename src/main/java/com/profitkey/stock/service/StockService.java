package com.profitkey.stock.service;

import com.profitkey.stock.config.ApiParser;
import com.profitkey.stock.dto.OpenApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
	private final OpenApiProperties openApiProperties;
	private final ApiParser apiParser;

	// @Value("${openapi.api-key}")
	// private String apiKey;
	// @Value("${openapi.stock-price-info.url}")
	// private String baseUrl;

	public String getStockInfo() {
		String enkey = openApiProperties.getApiKey();
		String baseUrl = openApiProperties.getStockPriceInfoUrl();
		String jsonStringfy = apiParser.fetchItemsAsJson(baseUrl, enkey);
		// URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
		// 	.queryParam("ServiceKey", ekey)
		// 	.queryParam("resultType", "json")
		// 	.queryParam("numOfRows", "3")
		// 	.queryParam("pageNo", "1")
		// 	.queryParam("basDt", "20250116")
		// 	.queryParam("beginFltRt", "1")
		// 	.build()
		// 	.toUri();

		return jsonStringfy;
	}
}
