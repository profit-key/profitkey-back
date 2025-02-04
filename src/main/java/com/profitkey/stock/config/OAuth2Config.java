package com.profitkey.stock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
public class OAuth2Config {
	// 소셜로그인 관련 필요 값들 모아둠
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(googleClientRegistration(), kakaoClientRegistration(),
			naverClientRegistration());
	}

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googleClientId;
	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String googleClientSecret;
	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String googleRedirectUri;
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String kakaoClientId;
	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String kakaoClientSecret;
	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String kakaoRedirectUri;
	@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String naverClientId;
	@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String naverClientSecret;
	@Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
	private String naverRedirectUri;

	@Bean
	public ClientRegistration googleClientRegistration() {
		return ClientRegistration.withRegistrationId("google")
			.clientId(googleClientId)
			.clientSecret(googleClientSecret)
			.redirectUri(googleRedirectUri)
			.scope("openid", "email", "profile_image")
			.authorizationUri("https://accounts.google.com/o/oauth2/auth")
			.tokenUri("https://oauth2.googleapis.com/token")
			.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
			.clientName("Google")
			.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs") // JWK Set URI
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 필수 추가
			.build();
	}

	private ClientRegistration kakaoClientRegistration() {
		return ClientRegistration.withRegistrationId("kakao")
			.clientId(kakaoClientId)
			.clientSecret(kakaoClientSecret)
			.redirectUri(kakaoRedirectUri)
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.scope("profile_nickname", "account_email")
			.authorizationUri("https://kauth.kakao.com/oauth/authorize")
			.tokenUri("https://kauth.kakao.com/oauth/token")
			.userInfoUri("https://kapi.kakao.com/v2/user/me")
			.userNameAttributeName("id")
			.clientName("Kakao")
			.build();
	}

	private ClientRegistration naverClientRegistration() {
		return ClientRegistration.withRegistrationId("naver")
			.clientId(naverClientId)
			.clientSecret(naverClientSecret)
			.redirectUri(naverRedirectUri)
			.scope("profile", "email")
			.authorizationUri("https://nid.naver.com/oauth2.0/authorize")
			.tokenUri("https://nid.naver.com/oauth2.0/token")
			.userInfoUri("https://openapi.naver.com/v1/nid/me")
			.clientName("Naver")
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 필수 추가
			.build();
	}

}