package com.profitkey.stock.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.entity.AuthProvider;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.jwt.CustomOAuth2UserService;
import com.profitkey.stock.jwt.JwtProvider;
import com.profitkey.stock.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final JwtProvider jwtProvider;

	@Operation(summary = SwaggerDocs.SUMMARY_KAKAO_LOGIN, description = SwaggerDocs.DESCRIPTION_KAKAO_LOGIN)
	@PostMapping("/login/oauth2/code/kakao")
	public ResponseEntity<String> kakaoLogin(
		@Parameter(description = "카카오 인가 코드", example = "kakao_authorization_code_here")
		@RequestParam("code") String code, HttpServletResponse response) {
		try {
			// 1. 카카오 API로부터 액세스 토큰을 얻기
			String accessToken = userService.getAccessTokenFromKakao(code);
			System.out.println("Access Token: " + accessToken);

			// 2. 액세스 토큰으로 사용자 정보 가져오기
			OAuth2User oAuth2User = customOAuth2UserService.loadUserFromAccessToken(accessToken);
			String email = (String)oAuth2User.getAttributes().get("email");
			String nickname = (String)oAuth2User.getAttributes().get("nickname");

			// 3. 사용자 등록/업데이트
			User user = userService.findOrCreateUser(email, accessToken, nickname, AuthProvider.KAKAO);

			// 4. JWT 생성
			String jwtToken = jwtProvider.createToken(user);

			// 5. JWT를 쿠키에 추가
			Cookie jwtCookie = new Cookie("accessToken", jwtToken);
			jwtCookie.setHttpOnly(true); // JavaScript 접근 금지
			jwtCookie.setSecure(false); // HTTPS에서만 전송 (로컬 개발 시 필요하면 false로 설정)
			jwtCookie.setPath("/"); // 모든 경로에서 유효
			jwtCookie.setMaxAge(3600); // 쿠키 만료 시간: 1시간
			response.addCookie(jwtCookie);

			// SameSite 속성 추가 (헤더를 직접 추가)
			response.addHeader("Set-Cookie",
				String.format("accessToken=%s; HttpOnly; Secure; SameSite=Strict; Path=/; Max-Age=3600",
					jwtToken));

			// 최종적으로 생성된 JWT 반환 (헤더와 쿠키 모두 설정됨)
			return ResponseEntity.ok("Login Success, jwt: " + jwtToken);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Error occurred during Kakao login: " + e.getMessage());
		}
	}

	@Operation(summary = SwaggerDocs.SUMMARY_TOKEN_LOGIN, description = SwaggerDocs.DESCRIPTION_TOKEN_LOGIN)
	@GetMapping("/get-token")
	public ResponseEntity<String> login(
		@Parameter(description = "사용자 이메일", example = "user@example.com")
		@RequestParam("email") String email,

		@Parameter(description = "소셜 로그인 제공자", example = "KAKAO")
		@RequestParam("provider") String provider) {
		try {
			// provider 값 검증
			AuthProvider authProvider;
			try {
				authProvider = AuthProvider.valueOf(provider.toUpperCase());
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid provider. Allowed values: KAKAO, NAVER, GOOGLE");
			}

			// 사용자 조회 또는 생성 (accessToken 없이 처리)
			User user = userService.findOrCreateUser(email, "", "", authProvider);

			// JWT 생성
			String jwtToken = jwtProvider.createToken(user);

			// JWT 반환
			return ResponseEntity.ok(jwtToken);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Error occurred during login: " + e.getMessage());
		}
	}
}
