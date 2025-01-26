package com.profitkey.stock.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		// 인증된 사용자 정보를 가져옴
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

		// OAuth2User에서 이메일과 닉네임 등의 정보 추출
		String email = (String)oAuth2User.getAttributes().get("email");
		String nickname = (String)oAuth2User.getAttributes().get("nickname");

		// 이메일을 기반으로 User 엔티티 조회 (없으면 새로 생성)
		User user = userRepository.findByEmail(email)
			.orElseGet(() -> {
				// 유저가 없으면 새로 생성
				return User.builder()
					.email(email)
					.nickname(nickname)
					.provider(AuthProvider.KAKAO) // 실제 제공자에 맞게 처리
					.build();
			});

		// JWT 액세스 토큰 생성
		String accessToken = jwtProvider.createToken(user);
		log.info("Generated JWT Access Token for user: {}", user.getEmail());

		// 클라이언트 측으로 직접 accessToken만 전달 (리디렉션 없이)
		response.getWriter().write("Access Token: " + accessToken); // 예시로 직접 보내는 방식
	}

}
