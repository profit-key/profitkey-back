package com.profitkey.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.entity.AuthProvider;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RestTemplate restTemplate;

	@Autowired
	public UserService(UserRepository userRepository, RestTemplate restTemplate) {
		this.userRepository = userRepository;
		this.restTemplate = restTemplate;
	}

	/**
	 * 카카오 API로부터 Access Token을 가져오는 메서드
	 * @param code Authorization Code
	 * @return Access Token
	 */
	// 액세스 토큰 요청 메서드
	public String getAccessTokenFromKakao(String code) throws JsonProcessingException {
		String url = "https://kauth.kakao.com/oauth/token";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", System.getenv("STOCK_KAKAO_CLIENT_ID"));  // 카카오 앱 REST API 키
		params.add("client_secret", System.getenv("STOCK_KAKAO_CLIENT_SECRET"));  // 클라이언트 시크릿 (있다면)
		params.add("redirect_uri", System.getenv("STOCK_KAKAO_REDIRECT_URI"));  // 리다이렉트 URI
		params.add("code", code);  // 인가 코드

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

			if (response.getStatusCode() != HttpStatus.OK) {
				throw new RuntimeException(
					"Error getting access token from Kakao. Status code: " + response.getStatusCode());
			}

			// 응답에서 액세스 토큰 추출
			String responseBody = response.getBody();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonResponse = objectMapper.readTree(responseBody);
			return jsonResponse.get("access_token").asText();
		} catch (Exception e) {
			throw new RuntimeException("Error retrieving access token: " + e.getMessage(), e);
		}
	}

	/**
	 * 이메일을 기준으로 유저가 있는지 확인하고, 없으면 생성
	 * @param email 사용자 이메일
	 * @param accessToken 카카오 액세스 토큰
	 * @param nickname 사용자 닉네임
	 * @param provider 소셜 로그인 제공자
	 * @return User 엔티티
	 */
	public User findOrCreateUser(String email, String accessToken, String nickname, AuthProvider provider) {
		User existingUser = userRepository.findByEmail(email);

		if (existingUser == null) {
			User newUser = User.builder()
				.email(email)
				.accessToken(accessToken)
				.nickname(nickname)
				.provider(provider)
				.build();
			return userRepository.save(newUser);
		} else {
			return existingUser;
		}
	}
}
