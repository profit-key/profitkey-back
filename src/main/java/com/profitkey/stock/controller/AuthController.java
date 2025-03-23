package com.profitkey.stock.controller;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.entity.Auth;
import com.profitkey.stock.service.AuthService;
import com.profitkey.stock.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	private final AuthService authService;
	private final JwtUtil jwtUtil;

	@Operation(summary = SwaggerDocs.SUMMARY_KAKAO_LOGIN,
		description = SwaggerDocs.DESCRIPTION_KAKAO_LOGIN)
	@GetMapping("/login/kakao")
	public ResponseEntity<?> kakaoLogin(@RequestParam("code") String accessCode,
		HttpServletResponse httpServletResponse) {
		Auth auth = authService.oAuthLogin(accessCode, httpServletResponse);
		String jwtToken = jwtUtil.generateToken(auth.getId(), auth.getEmail(), auth.getProvider());
		log.info("Generated JWT Token: {}", jwtToken);

		auth.setAccessToken(jwtToken);

		return ResponseEntity.ok(Map.of(
			"accessToken", jwtToken,
			"email", auth.getEmail()
		));
	}

	@Operation(summary = SwaggerDocs.SUMMARY_TOKEN_ISSUANCE,
		description = SwaggerDocs.DESCRIPTION_TOKEN_ISSUANCE)
	@PostMapping("/issuance")
	public ResponseEntity<?> issuance(@RequestParam("email") String email) {
		String accessToken = authService.issueToken(email);
		return ResponseEntity.ok(accessToken);
	}

	@Operation(summary = SwaggerDocs.SUMMARY_TOKEN_REFRESH,
		description = SwaggerDocs.DESCRIPTION_TOKEN_REFRESH)
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestHeader("Authorization") String token) {
		String newAccessToken = authService.refreshToken(token);
		return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
	}

	@Operation(summary = SwaggerDocs.SUMMARY_TOKEN_DISPOSE,
		description = SwaggerDocs.DESCRIPTION_TOKEN_DISPOSE)
	@PostMapping("/dispose")
	public ResponseEntity<?> dispose(@RequestHeader("Authorization") String token) {
		authService.disposeToken(token);
		return ResponseEntity.ok("정상처리되었습니다.");
	}

	@Operation(summary = SwaggerDocs.SUMMARY_LOGOUT, description = SwaggerDocs.DESCRIPTION_LOGOUT)
	@GetMapping("/logout/kakao")
	public ResponseEntity<?> kakaoLogout(HttpServletRequest request, HttpServletResponse response) {
		String jwtToken = authService.extractTokenFromRequest(request);
		authService.clearJwtCookie(response);
		authService.logout(request, response);
		authService.disposeToken(jwtToken);
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok("정상처리되었습니다.");
	}

	// @GetMapping("/logout/callback")
	// public ResponseEntity<?> logoutCallback(HttpServletRequest request, HttpServletResponse response) {
	// 	// JWT 토큰 가져오기
	// 	String jwtToken = authService.extractTokenFromRequest(request);
	//
	// 	// DB에서 JWT & 카카오 액세스 토큰 제거
	// 	authService.disposeToken(jwtToken);
	//
	// 	// 클라이언트 쿠키에서 JWT 삭제
	// 	authService.clearJwtCookie(response);
	//
	// 	// SecurityContext 초기화
	// 	SecurityContextHolder.clearContext();
	//
	// 	return ResponseEntity.ok("카카오 로그아웃 완료!");
	// }

}
