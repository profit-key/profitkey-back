package com.profitkey.stock.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Service
public class KakaoOAuthService {
	// 카카오 인증용 로직 - 카카오 api에서 제공하는 액세스 토큰을 통해 사용자 정보 가져옴

	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String redirectUri;

	private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";

	// 액세스 토큰 발급
	public String getAccessToken(String code) {
		// 파라미터 설정
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("code", code);
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("redirect_uri", redirectUri);

		// 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded");

		// HttpEntity 설정
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

		// RestTemplate 객체 생성
		RestTemplate restTemplate = new RestTemplate();

		try {
			// POST 요청 보내기
			ResponseEntity<String> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, entity, String.class);
			return response.getBody();  // JSON 응답 처리 (access_token 추출)
		} catch (Exception e) {
			System.err.println("Error occurred while getting access token: " + e.getMessage());
			return null;
		}
	}

	// 리프레시 토큰을 이용하여 새 액세스 토큰 발급
	public String refreshAccessToken(String refreshToken) {
		// 파라미터 설정
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "refresh_token");
		body.add("refresh_token", refreshToken);
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("redirect_uri", redirectUri);

		// 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded");

		// HttpEntity 설정
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

		// RestTemplate 객체 생성
		RestTemplate restTemplate = new RestTemplate();

		try {
			// POST 요청 보내기
			ResponseEntity<String> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, entity, String.class);
			return response.getBody();  // JSON 응답 처리 (access_token 추출)
		} catch (Exception e) {
			System.err.println("Error occurred while refreshing access token: " + e.getMessage());
			return null;
		}
	}

	// 초기화 출력
	@PostConstruct
	public void init() {
		System.out.println("Client ID: " + clientId);
		System.out.println("Client Secret: " + clientSecret);
		System.out.println("Redirect URI: " + redirectUri);
	}
}
