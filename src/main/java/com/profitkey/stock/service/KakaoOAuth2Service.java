package com.profitkey.stock.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.util.NicknameUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoOAuth2Service {
	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
	private String kakaoTokenUrl;
	@Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
	private String kakaoUserInfoUrl;
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String kakaoClientId;
	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String kakaoClientSecret;
	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String kakaoRedirectUri;

	public String getAccessToken(String code) {
		log.info("📌 받은 인가 코드: {}", code);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", kakaoClientId);
		params.add("redirect_uri", kakaoRedirectUri);
		params.add("code", code);
		params.add("client_secret", kakaoClientSecret);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		try {
			log.info("📌 카카오 토큰 요청 데이터: {}", params);
			log.info("📌 요청 URL: {}", kakaoTokenUrl);

			ResponseEntity<String> response = restTemplate.postForEntity(kakaoTokenUrl, request, String.class);

			log.info("✅ 카카오 응답 코드: {}", response.getStatusCode());
			log.info("✅ 카카오 응답 바디: {}", response.getBody());

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.getBody());
			return jsonNode.get("access_token").asText();
		} catch (HttpClientErrorException e) {
			log.error("❌ 카카오 요청 오류: {}", e.getStatusCode());
			log.error("❌ 응답 바디: {}", e.getResponseBodyAsString());
			throw new RuntimeException("Failed to get Kakao Access Token", e);
		} catch (Exception e) {
			log.error("카카오 토큰 요청 실패", e);
			throw new RuntimeException("Failed to get Kakao Access Token");
		}
	}

	public Map<String, Object> getUserInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(kakaoUserInfoUrl, HttpMethod.GET, entity, String.class);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.getBody());

			String id = jsonNode.get("id").asText();

			// 이메일이 없는 경우 예외 처리
			String email = null;
			JsonNode emailNode = jsonNode.get("kakao_account").get("email");
			if (emailNode != null && !emailNode.isNull()) {
				email = emailNode.asText();
			}

			// 기본적으로 랜덤 닉네임 설정
			String nickname = NicknameUtil.generateRandomNickname();

			// 카카오에서 닉네임이 제공되면 덮어쓰기
			if (jsonNode.has("properties") && jsonNode.get("properties").has("nickname")) {
				nickname = jsonNode.get("properties").get("nickname").asText();
			}

			// 이메일이 없을 때 랜덤 닉네임을 유지
			if (email == null || email.isEmpty()) {
				log.info("📌 이메일 없음 → 랜덤 닉네임 사용: {}", nickname);
			} else {
				log.info("📌 이메일 존재 → 카카오 닉네임 사용: {}", nickname);
			}

			Map<String, Object> userInfo = new HashMap<>();
			userInfo.put("id", id);
			userInfo.put("email", email);
			userInfo.put("nickname", nickname);

			return userInfo;
		} catch (Exception e) {
			log.error("카카오 사용자 정보 요청 실패", e);
			throw new RuntimeException("Failed to get Kakao User Info");
		}
	}

}
