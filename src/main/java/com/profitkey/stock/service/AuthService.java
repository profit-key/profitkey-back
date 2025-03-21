package com.profitkey.stock.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.profitkey.stock.entity.Auth;
import com.profitkey.stock.entity.AuthProvider;
import com.profitkey.stock.entity.UserInfo;
import com.profitkey.stock.repository.mypage.UserInfoRepository;
import com.profitkey.stock.repository.user.AuthRepository;
import com.profitkey.stock.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final KakaoOAuth2Service kakaoOAuth2Service;
	private final AuthRepository authRepository;
	private final UserInfoRepository userInfoRepository;
	private final JwtUtil jwtUtil;
	private final MyPageService myPageService;
	private final S3UploadService s3UploadService;

	public Auth oAuthLogin(String code, HttpServletResponse response) {
		String accessToken = kakaoOAuth2Service.getAccessToken(code);
		log.info("카카오 액세스 토큰: {}", accessToken); // 카카오 액세스 토큰 로그 출력

		Map<String, Object> userInfo = kakaoOAuth2Service.getUserInfo(accessToken);

		String email = (String)userInfo.get("email");
		String nickname = (String)userInfo.get("nickname");
		String profileImage = (String)userInfo.get("profileImage");

		// 회원 탈퇴 후 재가입 30일 제한 체크
		myPageService.checkRejoinRestriction(email);

		Optional<Auth> userOptional = authRepository.findByEmail(email);
		Auth auth = userOptional.orElseGet(() -> {
			Auth newAuth = Auth.builder()
				.email(email)
				.provider(AuthProvider.KAKAO)
				.accessToken(accessToken)
				.kakaoAccessToken(accessToken)  // 카카오 액세스 토큰 저장
				.build();

			Auth savedAuth = authRepository.save(newAuth);

			UserInfo newUserInfo = UserInfo.builder()
				.auth(savedAuth)
				.nickname(nickname)
				.profileImage(profileImage != null ? profileImage : "") // null 방지
				.build();

			userInfoRepository.save(newUserInfo);
			return savedAuth;
		});

		// UserInfo에서 닉네임 조회 추가
		String storedNickname = userInfoRepository.findByAuth(auth)
			.map(UserInfo::getNickname)
			.orElse(nickname); // 기존 닉네임이 없으면 카카오에서 가져온 닉네임 사용

		String jwtToken = jwtUtil.generateToken(auth.getId(), auth.getEmail(), auth.getProvider());
		log.info("oAuthLogin jwtToken : {} ", jwtToken);
		response.setHeader("Authorization", "Bearer " + jwtToken);

		//(추가) Auth 객체에 JWT토큰 저장
		auth.setAccessToken(jwtToken);
		// Auth에 액세스 토큰 추가
		auth.setKakaoAccessToken(accessToken);
		authRepository.save(auth);
		log.info("Auth 엔티티에 JWT 저장 완료: email={}, token={}", auth.getEmail(), jwtToken);

		return auth;
	}

	public String issueToken(String email) {
		Auth auth = authRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

		String accessToken = jwtUtil.generateToken(auth.getId(), auth.getEmail(), auth.getProvider());
		auth.setAccessToken(accessToken);
		authRepository.save(auth);

		return accessToken;
	}

	//  JWT 갱신 (Refresh Token 사용)
	public String refreshToken(String refreshToken) {
		String token = refreshToken.replace("Bearer ", "");

		if (!jwtUtil.validateToken(token)) {
			throw new RuntimeException("토큰 검증에 실패하였습니다.");
		}

		String id = jwtUtil.extractId(token);
		Auth auth = authRepository.findById(Long.valueOf(id))
			.orElseThrow(() -> new RuntimeException("id가 존재하지 않습니다."));

		if (!auth.getAccessToken().equals(token)) {
			throw new RuntimeException("토큰값이 일치하지 않습니다.");
		}

		return jwtUtil.generateToken(auth.getId(), auth.getEmail(), auth.getProvider());
	}

	//  JWT 폐기 (로그아웃)
	public void disposeToken(String token) {
		String jwtToken = token.replace("Bearer ", "");

		// 토큰 검증
		if (!jwtUtil.validateToken(jwtToken)) {
			log.error("JWT 토큰 검증 실패: {}", jwtToken);
			throw new RuntimeException("토큰 검증에 실패하였습니다.");
		}

		// JWT에서 이메일 추출
		String email = jwtUtil.extractClaims(jwtToken).get("email").toString();
		log.info("로그아웃 요청된 이메일: {}", email);

		// 이메일로 사용자 조회
		Auth auth = authRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

		// JWT 토큰 무효화
		auth.setAccessToken(null);
		// 카카오 액세스 토큰도 null로 설정하여 제거
		auth.setKakaoAccessToken(null);

		authRepository.save(auth);
		log.info("토큰 무효화 완료: 이메일 = {}", email);
		// 디비업데이트만

		// 임시추가 -> 위치나 방식 변경필요
		// SecurityContextHolder.clearContext();
	}

	// ✅ Auth 객체로부터 닉네임 조회하는 메서드 추가
	public String getNickname(Auth auth) {
		return userInfoRepository.findByAuth(auth)
			.map(UserInfo::getNickname)
			.orElse(null); // UserInfo가 없으면 null 반환
	}

	// (추가) access token 으로 내 정보 불러오기
	// AuthService에 사용자 정보 반환 메서드 추가
	public Map<String, Object> getUserInfoFromToken(HttpServletRequest request) {
		String token = extractTokenFromRequest(request); // 요청에서 토큰 추출

		String id = jwtUtil.extractId(token);

		Auth auth = authRepository.findById(Long.valueOf(id))
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

		UserInfo userInfo = userInfoRepository.findByAuth(auth)
			.orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

		// 프로필 이미지가 존재하면 S3 URL을 반환하고, 없으면 빈 문자열 처리
		String profileImageUrl = "";  // 기본값으로 빈 문자열 할당

		if (userInfo.getProfileImage() != null && !userInfo.getProfileImage().isEmpty()) {
			profileImageUrl = s3UploadService.getFileUrl(userInfo.getProfileImage());
		}

		return Map.of(
			"email", auth.getEmail(),
			"userId", userInfo.getUserId(),
			"nickname", userInfo.getNickname(),
			"profileImage", profileImageUrl  // S3 URL 또는 빈 문자열 반환
		);
	}

	// HTTP 요청에서 토큰 추출
	public String extractTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith("Bearer ")) {
			return token.substring(7);  // "Bearer "를 제외한 실제 토큰 반환
		}
		throw new RuntimeException("Authorization 헤더가 없습니다.");
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		// 1. 요청에서 JWT 토큰 추출
		String token = extractTokenFromRequest(request);
		if (token == null || token.isEmpty()) {
			throw new RuntimeException("JWT 토큰이 제공되지 않았습니다.");
		}

		String email = null;
		try {
			// 2. 토큰에서 이메일 추출
			Claims claims = jwtUtil.extractClaims(token);  // Claims 객체 반환
			email = claims.get("email").toString();  // 이메일 필드 추출
		} catch (Exception e) {
			throw new RuntimeException("토큰에서 이메일을 추출하는 데 실패했습니다.", e);
		}

		// 3. 이메일을 기반으로 사용자 정보 찾기
		Auth auth = authRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

		// 4. 카카오 로그아웃 처리 (카카오 액세스 토큰 만료)
		// kakaoOAuth2Service.logout(auth.getAccessToken());
		kakaoOAuth2Service.logout(auth.getKakaoAccessToken());

		// 5. JWT 토큰 폐기
		disposeToken(token);

		// 6. 로그아웃 성공 응답 반환
		response.setStatus(HttpServletResponse.SC_OK); // 200 OK 응답
	}

	//쿠키 삭제
	public void clearJwtCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("Authorization", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

}
