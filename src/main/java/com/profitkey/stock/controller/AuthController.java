package com.profitkey.stock.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.profitkey.stock.docs.SwaggerDocs;
import com.profitkey.stock.entity.Auth;
import com.profitkey.stock.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	private final AuthService authService;

	@Operation(summary = SwaggerDocs.SUMMARY_KAKAO_LOGIN,
		description = SwaggerDocs.DESCRIPTION_KAKAO_LOGIN)
	@GetMapping("/login/kakao")
	public ResponseEntity<?> kakaoLogin(@RequestParam("code") String accessCode,
		HttpServletResponse httpServletResponse) {
		Auth auth = authService.oAuthLogin(accessCode, httpServletResponse);
		return ResponseEntity.ok(auth);
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

	// /me 엔드포인트 추가
	@Operation(summary = SwaggerDocs.SUMMARY_TOKEN_ME, description = SwaggerDocs.DESCRIPTION_TOKEN_ME)
	@GetMapping("/me")
	public ResponseEntity<Map<String, Object>> getCurrentUserInfo(@RequestHeader("Authorization") String token) {
		// Authorization 헤더에서 Bearer 토큰을 추출
		String accessToken = token.replace("Bearer ", "");

		// 토큰으로부터 사용자 정보 가져오기
		Map<String, Object> userInfo = authService.getUserInfoFromToken(accessToken);

		return ResponseEntity.ok(userInfo);  // 사용자 정보 반환
	}
}
