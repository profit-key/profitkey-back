package com.profitkey.stock.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
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
import com.profitkey.stock.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
		// (추가) Auth에 저장된 JWT 토큰 가져오기
		String jwtToken = jwtUtil.generateToken(auth.getId(), auth.getEmail(), auth.getProvider());
		log.info("Generated JWT Token: {}", jwtToken);

		// return ResponseEntity.ok(auth);

		// (추가) JWT 응답 객체에 담아서 반환
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

	@Operation(summary = SwaggerDocs.SUMMARY_LOGOUT,
		description = SwaggerDocs.DESCRIPTION_LOGOUT)
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7); // Bearer 제거

			try {
				// JWT 토큰을 검증하고 필요한 처리를 합니다
				boolean isTokenValid = jwtUtil.validateToken(token);
				if (isTokenValid) {
					// JWT를 무효화 처리 (예: 블랙리스트에 추가 등)
					authService.disposeToken(token);
					return ResponseEntity.ok("Logged out successfully");
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error during logout: " + e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Authorization Token");
	}

}
