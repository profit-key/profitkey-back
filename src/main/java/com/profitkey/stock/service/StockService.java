package com.profitkey.stock.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.profitkey.stock.dto.OpenApiProperties;
import com.profitkey.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
	private final StockRepository stockRepository;
	private final OpenApiProperties openApiProperties;

	//    @Value("${openapi.api-key}")
	//    private String apiKey;
	//    @Value("${openapi.stock-price-info.url}")
	//    private String baseUrl;

	public String getStockInfo() throws IOException {
		StringBuilder urlBuilder = new StringBuilder(openApiProperties.getStockPriceInfoUrl());
		urlBuilder.append(
			"?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + openApiProperties.getApiKey());  // API 키
		urlBuilder.append(
			"&" + URLEncoder.encode("resultType", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));  // JSON 형식
		urlBuilder.append(
			"&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));    // 페이지 당 결과 수
		urlBuilder.append(
			"&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));        // 페이지 번호
		urlBuilder.append(
			"&" + URLEncoder.encode("cond[country_nm::EQ]", "UTF-8") + "=" + URLEncoder.encode("가나", "UTF-8"));  // 국가명
		urlBuilder.append("&" + URLEncoder.encode("cond[country_iso_alp2::EQ]", "UTF-8") + "=" + URLEncoder.encode("GH",
			"UTF-8"));  // ISO 코드

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		return sb.toString();
	}
}
