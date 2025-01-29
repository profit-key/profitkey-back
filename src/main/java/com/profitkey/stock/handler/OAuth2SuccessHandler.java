package com.profitkey.stock.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitkey.stock.entity.AuthProvider;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.jwt.JwtProvider;
import com.profitkey.stock.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		// 인증된 사용자 정보를 가져옴
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

		// OAuth2User에서 이메일과 닉네임 등의 정보 추출
		String email = (String)oAuth2User.getAttributes().get("email");
		String nickname = (String)oAuth2User.getAttributes().get("nickname");
		String provider = "kakao";

		// 이메일을 기반으로 User 엔티티 조회 (없으면 새로 생성)
		User user = userRepository.findByEmail(email)
			.orElseGet(() -> {
				log.info("새로운 유저 탐지. 새로운 유저 생성{}" + email);
				return userRepository.save(User.builder() // 아래 정보 DB에 저장!
					.email(email)
					.nickname(nickname)
					.provider(AuthProvider.KAKAO) // 실제 제공자에 맞게 처리
					.build());
			});

		// JWT 액세스 토큰 생성
		String accessToken = jwtProvider.createToken(user);
		log.info("Generated JWT Access Token for user: {}", user.getEmail());

		// JWT 리프레시 토큰 생성
		String refreshToken = jwtProvider.refreshToken(accessToken, user);
		log.info("Generated JWT Refresh Token for user: {}", user.getEmail());

		// 클라이언트로 리프레시 토큰도 함께 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// 응답 데이터를 JSON으로 변환
		var tokenResponse = new TokenResponse(accessToken);
		response.getWriter().write(objectMapper.writeValueAsString(tokenResponse));
	}

	// 응답용 DTO
	private record TokenResponse(String accessToken) {
	}
}

