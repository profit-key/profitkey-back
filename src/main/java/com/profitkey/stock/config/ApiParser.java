package com.profitkey.stock.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApiParser {

	private final ObjectMapper objectMapper;
	private final ApiConfig apiConfig;

	public String fetchItemsAsJson(String baseUrl, String enkey) {
		try {
			URI uri = createRequestUri(baseUrl, enkey);
			ResponseEntity<String> response = sendRequest(uri);
			return extractItemsAsJson(response.getBody());
		} catch (Exception e) {
			throw new RuntimeException("SERVICE_KEY_IS_NOT_REGISTERED_ERROR", e);
		}
	}

	private URI createRequestUri(String baseUrl, String enkey) {
		return URI.create(String.format("%s?ServiceKey=%s&resultType=json", baseUrl, enkey));
	}

	private ResponseEntity<String> sendRequest(URI uri) {
		log.info("uri : {}", uri);
		HttpEntity<String> entity = apiConfig.createHttpEntity();
		return apiConfig.restTemplate().exchange(uri, HttpMethod.GET, entity, String.class);
	}

	private String extractItemsAsJson(String responseBody) throws Exception {
		JsonNode rootNode = objectMapper.readTree(responseBody);
		JsonNode itemsNode = rootNode.at("/response/body/items/item");
		return itemsNode.toPrettyString();
	}
}
