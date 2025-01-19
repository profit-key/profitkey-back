package com.profitkey.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class OAuth2Config {
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(
			googleClientRegistration(),
			kakaoClientRegistration(),
			naverClientRegistration()
		);
	}

	@Bean
	public ClientRegistration googleClientRegistration() {
		return ClientRegistration.withRegistrationId("google")
			.clientId(System.getenv("STOCK_GOOGLE_CLIENT_ID"))
			.clientSecret(System.getenv("STOCK_GOOGLE_CLIENT_SECRET"))
			.redirectUri("STOCK_GOOGLE_REDIRECT_URI")
			.scope("openid", "email")
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
			.clientId(System.getenv("STOCK_KAKAO_CLIENT_ID"))
			.clientSecret(System.getenv("STOCK_KAKAO_CLIENT_SECRET"))
			.redirectUri("STOCK_KAKAO_REDIRECT_URI")
			.scope("profile_nickname", "account_email")
			.authorizationUri("https://kauth.kakao.com/oauth/authorize")
			.tokenUri("https://kauth.kakao.com/oauth/token")
			.userInfoUri("https://kapi.kakao.com/v2/user/me")
			.clientName("Kakao")
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 필수 추가
			.build();
	}

	private ClientRegistration naverClientRegistration() {
		return ClientRegistration.withRegistrationId("naver")
			.clientId(System.getenv("STOCK_NAVER_CLIENT_ID"))
			.clientSecret(System.getenv("STOCK_NAVER_CLIENT_SECRET"))
			.redirectUri("STOCK_NAVER_REDIRECT_URI")
			.scope("profile", "email")
			.authorizationUri("https://nid.naver.com/oauth2.0/authorize")
			.tokenUri("https://nid.naver.com/oauth2.0/token")
			.userInfoUri("https://openapi.naver.com/v1/nid/me")
			.clientName("Naver")
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 필수 추가
			.build();
	}

}