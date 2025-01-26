package com.profitkey.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.profitkey.stock.entity.AuthProvider;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.jwt.CustomOAuth2UserService;
import com.profitkey.stock.jwt.JwtProvider;
import com.profitkey.stock.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private JwtProvider jwtProvider;

	@GetMapping("/login/oauth2/code/kakao")
	public String kakaoLogin(@RequestParam("code") String code) {
		// 인가 코드 출력
		System.out.println("Method kakaoLogin called");
		System.out.println("Authorization Code: " + code);

		// 1. 카카오 API로부터 액세스 토큰을 얻기
		String accessToken = null;
		try {
			accessToken = userService.getAccessTokenFromKakao(code);
		} catch (Exception e) {
			e.printStackTrace();
			return "Error retrieving access token: " + e.getMessage();
		}

		// 액세스 토큰 출력
		System.out.println("Access Token: " + accessToken);

		// 2. 액세스 토큰으로 사용자 정보 가져오기
		OAuth2User oAuth2User = null;
		try {
			oAuth2User = customOAuth2UserService.loadUserFromAccessToken(accessToken);
		} catch (Exception e) {
			e.printStackTrace();
			return "Error retrieving user info: " + e.getMessage();
		}

		String email = (String)oAuth2User.getAttributes().get("email");
		String nickname = (String)oAuth2User.getAttributes().get("nickname");

		// 3. 사용자 등록/업데이트
		User user = null;
		try {
			user = userService.findOrCreateUser(email, accessToken, nickname, AuthProvider.KAKAO);
		} catch (Exception e) {
			e.printStackTrace();
			return "Error creating or finding user: " + e.getMessage();
		}

		// 4. JWT 생성
		String jwtToken = null;
		try {
			jwtToken = jwtProvider.createToken(user);
		} catch (Exception e) {
			e.printStackTrace();
			return "Error creating JWT token: " + e.getMessage();
		}

		// 최종적으로 생성된 JWT 반환
		return jwtToken;
	}
}
