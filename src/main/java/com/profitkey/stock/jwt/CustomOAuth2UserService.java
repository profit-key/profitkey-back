package com.profitkey.stock.jwt;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.profitkey.stock.entity.AuthProvider;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		// 사용자 정보 처리
		Map<String, Object> attributes = oAuth2User.getAttributes();
		String providerString = userRequest.getClientRegistration().getRegistrationId();

		// 디버깅용 로그 추가
		log.info("Provider string: {}", providerString);
		log.info("Attributes: {}", attributes);

		if (providerString == null) {
			throw new IllegalArgumentException("Provider string is null. Check the client registration.");
		}

		if ("kakao".equals(providerString)) {
			Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
			String email = (String)kakaoAccount.get("email");
			String nickname = (String)((Map<String, Object>)attributes.get("properties")).get("nickname");

			// 디버깅용 로그 추가
			log.info("Kakao Account: email={}, nickname={}", email, nickname);

			// User 엔티티로 변환
			User user = userRepository.findByEmail(email)
				.orElseGet(() -> User.builder()
					.email(email)
					.provider(AuthProvider.KAKAO) // provider는 적절한 값을 넣어야 함
					.accessToken((String)attributes.get("access_token")) // 액세스 토큰은 다른 방식으로 받아야 할 수 있음
					.nickname(nickname)
					.build());

			// OAuth2User 객체로 변환 (여기서는 email을 기본으로 사용)
			return new DefaultOAuth2User(
				List.of(new SimpleGrantedAuthority("ROLE_USER")),
				Map.of("email", email, "nickname", nickname),
				"email"
			);
		}

		// 다른 OAuth2 제공자 처리 로직
		return oAuth2User;
	}

	// 액세스 토큰으로 사용자 정보를 직접 로드
	public OAuth2User loadUserFromAccessToken(String accessToken) {
		// Kakao 사용자 정보 API URL
		String userInfoEndpointUri = "https://kapi.kakao.com/v2/user/me";

		// HTTP 요청을 통해 사용자 정보를 가져옵니다.
		Map<String, Object> response = restTemplate.getForObject(
			userInfoEndpointUri,
			Map.class,
			Map.of("Authorization", "Bearer " + accessToken)
		);

		// 사용자 정보 파싱
		Map<String, Object> kakaoAccount = (Map<String, Object>)response.get("kakao_account");
		String email = (String)kakaoAccount.get("email");
		String nickname = (String)((Map<String, Object>)response.get("properties")).get("nickname");

		// OAuth2User 객체로 변환
		return new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("ROLE_USER")),
			Map.of("email", email, "nickname", nickname), "email");
	}

	// 액세스 토큰을 리프레시 토큰을 이용해 갱신하는 메서드
	public String refreshAccessToken(String refreshToken, String clientId, String clientSecret, String redirectUri) {
		// 리프레시 토큰을 사용하여 액세스 토큰을 갱신하는 API 호출
		String tokenUrl = "https://kauth.kakao.com/oauth/token";
		RestTemplate restTemplate = new RestTemplate();

		// 리프레시 토큰을 갱신하기 위한 요청 URL 생성
		String url = UriComponentsBuilder.fromHttpUrl(tokenUrl)
			.queryParam("grant_type", "refresh_token")
			.queryParam("refresh_token", refreshToken)
			.queryParam("client_id", clientId)
			.queryParam("client_secret", clientSecret)
			.queryParam("redirect_uri", redirectUri)
			.toUriString();

		// 요청 보내기
		ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

		// 응답에서 새로운 액세스 토큰을 추출 (예시: JSON 파싱)
		return response.getBody();  // 실제로는 JSON에서 access_token을 추출하는 로직을 추가해야 함
	}
}
