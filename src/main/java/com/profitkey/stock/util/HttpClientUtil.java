package com.profitkey.stock.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientUtil {

	public static String sendGetRequest(URL url, Map<String, String> headers, Object requestParam) {
		HttpURLConnection conn = null;
		BufferedReader br = null;

		try {
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");

			// 공통 헤더 구성
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}

			// API 별 헤더 구성
			Map<String, String> dtoHeaders = HeaderUtil.getHeadersFromDto(requestParam);
			for (Map.Entry<String, String> entry : dtoHeaders.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}

			conn.connect();

			int responseCode = conn.getResponseCode();
			System.out.println("HTTP 응답 코드: " + responseCode);

			String responseMessage = readResponse(conn, responseCode);
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

	public static String sendPostRequest(String urlData, String paramData) {
		HttpURLConnection conn = null;
		BufferedReader br = null;

		try {
			URL url = new URL(urlData);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			try (OutputStream os = conn.getOutputStream()) {
				byte[] requestData = paramData.getBytes(StandardCharsets.UTF_8);
				os.write(requestData);
			}

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

	private static String readResponse(HttpURLConnection conn, int responseCode) throws IOException {
		BufferedReader br = new BufferedReader(
			new InputStreamReader(responseCode == 200 ? conn.getInputStream() : conn.getErrorStream())
		);
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			response.append(line);
		}
		return response.toString();
	}
}
