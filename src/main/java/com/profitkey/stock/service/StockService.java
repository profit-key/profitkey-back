package com.profitkey.stock.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.config.ApiParser;
import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.dto.openapi.OpenApiProperties;
import com.profitkey.stock.dto.request.stock.InquirePriceRequest;
import com.profitkey.stock.util.HeaderUtil;
import com.profitkey.stock.util.HttpClientUtil;
import java.io.IOException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
	private final OpenApiProperties openApiProperties;
	private final KisApiProperties kisApiProperties;
	private final ApiParser apiParser;

	public String getStockInfo() {
		String enkey = openApiProperties.getApiKey();
		String baseUrl = openApiProperties.getStockPriceInfoUrl();
		String jsonStringfy = apiParser.fetchItemsAsJson(baseUrl, enkey);

		return jsonStringfy;
	}

	public String getToken() throws IOException {
		String url = kisApiProperties.getDevApiUrl() + kisApiProperties.getOauth2TokenUrl();
		String data = """
			{
			    "grant_type": "client_credentials",
			    "appkey": "%s",
			    "appsecret": "%s"
			}
			""".formatted(kisApiProperties.getApiKey(), kisApiProperties.getSecretKey());
		;

		String jsonResponse = HttpClientUtil.sendPostRequest(url, data);
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonResponse);
		return rootNode.get("access_token").asText();
	}

	public ResponseEntity<Object> getInquirePrice(InquirePriceRequest request) {
		Object result = null;
		String urlData = kisApiProperties.getDevApiUrl() + kisApiProperties.getInquirePriceUrl();
		String trId = request.getTr_id();               // 거래ID
		String mrktDivCode = request.getMrktDivCode();  // FID 조건 시장 분류 코드
		String fidInput = request.getFidInput();        // FID 입력 종목코드
		String paramData = String.format("?fid_cond_mrkt_div_code=%s&fid_input_iscd=%s", mrktDivCode, fidInput);
		String fullUrl = urlData.trim() + paramData;

		InquirePriceRequest requestParam = new InquirePriceRequest(trId, mrktDivCode, fidInput);

		log.info("full url : {}", fullUrl);
		try {
			URL url = new URL(fullUrl);
			String jsonString = HttpClientUtil.sendGetRequest(url, HeaderUtil.getCommonHeaders(), requestParam);
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(jsonString, Object.class);
		} catch (IOException e) {
			e.getMessage();
		}

		return ResponseEntity.ok(result);
	}

}
