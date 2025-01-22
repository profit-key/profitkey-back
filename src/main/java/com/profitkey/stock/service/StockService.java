package com.profitkey.stock.service;

import com.profitkey.stock.config.ApiParser;
import com.profitkey.stock.dto.KisApiProperties;
import com.profitkey.stock.dto.OpenApiProperties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

		return httpPostConnection(url, data);
	}

	public String getInquirePrice() throws IOException {
		String url = kisApiProperties.getDevApiUrl() + kisApiProperties.getInquirePriceUrl();
		String tr_id = "FHKST01010100";
		String data = "?fid_cond_mrkt_div_code=J" + //FID 조건 시장 분류 코드
			"&fid_input_iscd=000660"; //FID 입력 종목코드
		// 000660 하이닉스
		return httpGetConnection(url, data, tr_id);
	}

	public String httpGetConnection(String urlData, String paramData, String trId) {
		String fullUrl = urlData.trim() + paramData;
		HttpURLConnection conn = null;
		BufferedReader br = null;

		try {
			URL url = new URL(fullUrl);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("authorization", "Bearer "
				+ "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6IjZiYWIxNTBmLWViNGQtNDM3Ny05NTJiLWM3YzMxZTM0M2U1MCIsInByZHRfY2QiOiIiLCJpc3MiOiJ1bm9ndyIsImV4cCI6MTczNzYyMDc0OCwiaWF0IjoxNzM3NTM0MzQ4LCJqdGkiOiJQU3hOZUJuY25EbE1GOGlnVDh3MzlaeVBkakFJb2k2ZGhDWjYifQ.bmh26Rcj_47fJD-G_71-KL5q9BPg2RYcTJGthcw9s1UiiiaMI8dVJ222kmDMoI2qi74EcRHRSE6pGIHvBtyRBQ");
			conn.setRequestProperty("appKey", kisApiProperties.getApiKey());
			conn.setRequestProperty("appSecret", kisApiProperties.getSecretKey());
			// conn.setRequestProperty("personalSeckey", "{personalSeckey}");
			conn.setRequestProperty("tr_id", trId);
			conn.setRequestProperty("tr_cont", " ");
			// conn.setRequestProperty("custtype", "법인(B), 개인(P)");
			conn.setRequestProperty("custtype", "P");
			// conn.setRequestProperty("seq_no", "법인(01), 개인( )");
			conn.setRequestProperty("seq_no", " ");
			// conn.setRequestProperty("mac_address", "{Mac_address}");
			// conn.setRequestProperty("phone_num", "P01011112222");
			// conn.setRequestProperty("ip_addr", "{IP_addr}");
			// conn.setRequestProperty("hashkey", "{Hash값}");
			// conn.setRequestProperty("gt_uid", "{Global UID}");
			conn.setDoOutput(true);

			// Connect and read response
			conn.connect();
			int responseCode = conn.getResponseCode();
			String responseMessage = readResponse(conn, responseCode);

			System.out.println("HTTP 응답 코드: " + responseCode);
			System.out.println("HTTP 응답 데이터: " + responseMessage);

			return responseMessage;
		} catch (IOException e) {
			throw new RuntimeException("HTTP 요청 중 오류 발생", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.err.println("응답 스트림 닫기 실패: " + e.getMessage());
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public String httpPostConnection(String urlData, String paramData) {
		HttpURLConnection conn = null;
		BufferedReader br = null;

		try {
			// Set up the connection
			URL url = new URL(urlData);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			// Write body data
			try (OutputStream os = conn.getOutputStream()) {
				byte[] requestData = paramData.getBytes(StandardCharsets.UTF_8);
				os.write(requestData);
			}

			// Handle the response
			int responseCode = conn.getResponseCode();
			System.out.println("HTTP 응답 코드: " + responseCode);

			String responseMessage = readResponse(conn, responseCode);
			System.out.println("HTTP 응답 데이터: " + responseMessage);

			return responseMessage;
		} catch (IOException e) {
			throw new RuntimeException("API 호출 중 오류 발생", e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	private String getBearerToken() {
		return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6IjZiYWIxNTBmLWViNGQtNDM3Ny05NTJiLWM3YzMxZTM0M2U1MCIsInByZHRfY2QiOiIiLCJpc3MiOiJ1bm9ndyIsImV4cCI6MTczNzYyMDc0OCwiaWF0IjoxNzM3NTM0MzQ4LCJqdGkiOiJQU3hOZUJuY25EbE1GOGlnVDh3MzlaeVBkakFJb2k2ZGhDWjYifQ.bmh26Rcj_47fJD-G_71-KL5q9BPg2RYcTJGthcw9s1UiiiaMI8dVJ222kmDMoI2qi74EcRHRSE6pGIHvBtyRBQ";
	}

	private String readResponse(HttpURLConnection conn, int responseCode) throws IOException {
		BufferedReader br = null;
		try {
			if (responseCode >= 200 && responseCode < 300) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}
}
