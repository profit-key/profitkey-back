package com.profitkey.stock.service;

import com.profitkey.stock.entity.AuthProvider;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.repository.user.UserRepository;
import com.profitkey.stock.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final KakaoOAuth2Service kakaoOAuth2Service;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	public User oAuthLogin(String code, HttpServletResponse response) {
		String accessToken = kakaoOAuth2Service.getAccessToken(code);
		Map<String, Object> userInfo = kakaoOAuth2Service.getUserInfo(accessToken);

		String email = (String)userInfo.get("email");
		String nickname = (String)userInfo.get("nickname");

		Optional<User> userOptional = userRepository.findByEmail(email);
		User user = userOptional.orElseGet(() -> {
			User newUser = new User(email, AuthProvider.KAKAO, accessToken, nickname);
			return userRepository.save(newUser);
		});

		String jwtToken = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getProvider());
		log.info("oAuthLogin jwtToken : {} ", jwtToken);
		response.setHeader("Authorization", "Bearer " + jwtToken);

		return user;
	}

	public String issueToken(String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

		String accessToken = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getProvider());
		user.setAccessToken(accessToken);
		userRepository.save(user);

		return accessToken;
	}

	// 🔥 JWT 갱신 (Refresh Token 사용)
	public String refreshToken(String refreshToken) {
		String token = refreshToken.replace("Bearer ", "");

		if (!jwtUtil.validateToken(token)) {
			throw new RuntimeException("토큰 검증에 실패하였습니다.");
		}

		String email = jwtUtil.extractEmail(token);
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

		if (!user.getAccessToken().equals(token)) {
			throw new RuntimeException("토큰값이 일치하지 않습니다.");
		}

		return jwtUtil.generateToken(user.getId(), user.getEmail(), user.getProvider());
	}

	// 🔥 JWT 폐기 (로그아웃)
	public void disposeToken(String token) {
		String jwtToken = token.replace("Bearer ", "");
		String email = jwtUtil.extractClaims(jwtToken).get("email").toString();

		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

		user.setAccessToken(null);
		userRepository.save(user);
	}
}
