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

	// public Auth oAuthLogin(String code, HttpServletResponse response) {
	// 	String accessToken = kakaoOAuth2Service.getAccessToken(code);
	// 	Map<String, Object> userInfo = kakaoOAuth2Service.getUserInfo(accessToken);
	//
	// 	String email = (String)userInfo.get("email");
	// 	String nickname = (String)userInfo.get("nickname");
	// 	String profileImage = (String)userInfo.get("profileImage");
	//
	// 	// 회원 탈퇴 후 재가입 30일 제한 체크
	// 	myPageService.checkRejoinRestriction(email);
	//
	// 	Optional<Auth> userOptional = authRepository.findByEmail(email);
	// 	Auth auth = userOptional.orElseGet(() -> {
	// 		Auth newAuth = Auth.builder()
	// 			.email(email)
	// 			.provider(AuthProvider.KAKAO)
	// 			.accessToken(accessToken)
	// 			.build();
	//
	// 		Auth savedAuth = authRepository.save(newAuth);
	//
	// 		UserInfo newUserInfo = UserInfo.builder()
	// 			.auth(savedAuth)
	// 			.nickname(nickname)
	// 			.profileImage(profileImage)
	// 			.build();
	//
	// 		userInfoRepository.save(newUserInfo);
	//
	// 		return savedAuth;
	// 	});
	//
	// 	String jwtToken = jwtUtil.generateToken(auth.getId(), auth.getEmail(), auth.getProvider());
	// 	log.info("oAuthLogin jwtToken : {} ", jwtToken);
	// 	response.setHeader("Authorization", "Bearer " + jwtToken);
	//
	// 	return auth;
	// }

	public Auth oAuthLogin(String code, HttpServletResponse response) {
		String accessToken = kakaoOAuth2Service.getAccessToken(code);
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
				.build();

			Auth savedAuth = authRepository.save(newAuth);

			UserInfo newUserInfo = UserInfo.builder()
				.auth(savedAuth)
				.nickname(nickname)
				.profileImage(profileImage)
				.build();

			userInfoRepository.save(newUserInfo);
			return savedAuth;
		});

		// ✅ UserInfo에서 닉네임 조회 추가
		String storedNickname = userInfoRepository.findByAuth(auth)
			.map(UserInfo::getNickname)
			.orElse(nickname); // 기존 닉네임이 없으면 카카오에서 가져온 닉네임 사용

		String jwtToken = jwtUtil.generateToken(auth.getId(), auth.getEmail(), auth.getProvider());
		log.info("oAuthLogin jwtToken : {} ", jwtToken);
		response.setHeader("Authorization", "Bearer " + jwtToken);

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

		String email = jwtUtil.extractEmail(token);
		Auth auth = authRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

		if (!auth.getAccessToken().equals(token)) {
			throw new RuntimeException("토큰값이 일치하지 않습니다.");
		}

		return jwtUtil.generateToken(auth.getId(), auth.getEmail(), auth.getProvider());
	}

	//  JWT 폐기 (로그아웃)
	public void disposeToken(String token) {
		String jwtToken = token.replace("Bearer ", "");
		String email = jwtUtil.extractClaims(jwtToken).get("email").toString();

		Auth auth = authRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

		auth.setAccessToken(null);
		authRepository.save(auth);
	}

	// ✅ Auth 객체로부터 닉네임 조회하는 메서드 추가
	public String getNickname(Auth auth) {
		return userInfoRepository.findByAuth(auth)
			.map(UserInfo::getNickname)
			.orElse(null); // UserInfo가 없으면 null 반환
	}

	// (추가) access token 으로 내 정보 불러오기
	// AuthService에 사용자 정보 반환 메서드 추가
	public Map<String, Object> getUserInfoFromToken(String token) {
		String email = jwtUtil.extractEmail(token);

		Auth auth = authRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

		UserInfo userInfo = userInfoRepository.findByAuth(auth)
			.orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

		// 프로필 이미지가 존재하면 S3 URL을 반환하고, 없으면 빈 문자열 처리
		String profileImageUrl =
			userInfo.getProfileImage().isEmpty() ? "" : s3UploadService.getFileUrl(userInfo.getProfileImage());

		return Map.of(
			"email", auth.getEmail(),
			"userId", userInfo.getUserId(),
			"nickname", userInfo.getNickname(),
			"profileImage", profileImageUrl  // S3 URL 또는 빈 문자열 반환
		);
	}

}
