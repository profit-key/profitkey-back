package com.profitkey.stock.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.profitkey.stock.entity.AuthProvider;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		DefaultOAuth2User oAuth2User = (DefaultOAuth2User)authentication.getPrincipal();
		String email = (String)oAuth2User.getAttributes().get("email");
		String nickname = (String)oAuth2User.getAttributes().get("nickname");
		String providerString = (String)oAuth2User.getAttributes().get("provider");

		log.info("OAuth2User email: {}, nickname: {}, provider: {}", email, nickname, providerString);

		if (providerString == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Provider information is missing");
			return;
		}

		AuthProvider provider;
		try {
			provider = AuthProvider.valueOf(providerString.toUpperCase());
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid provider: " + providerString);
			return;
		}

		User user = User.builder()
			.email(email)
			.nickname(nickname)
			.provider(provider)
			.build();

		String jwtToken = jwtProvider.createToken(user);

		response.addHeader("Authorization", "Bearer " + jwtToken);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"token\":\"" + jwtToken + "\"}");
	}

}
