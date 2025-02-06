package com.profitkey.stock.service;

import com.profitkey.stock.entity.RefreshTokenEntity;
import com.profitkey.stock.repository.user.RefreshTokenRepository;
import com.profitkey.stock.util.JwtUtil;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final JwtUtil jwtUtil;
	private final RefreshTokenRepository refreshTokenRepository;

	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		OAuth2User oauth2User = super.loadUser(userRequest);

		Map<String, Object> attributes = oauth2User.getAttributes();
		String email = ((Map<String, Object>)attributes.get("kakao_account")).get("email").toString();

		String accessToken = jwtUtil.generateToken(email);
		String refreshToken = jwtUtil.generateRefreshToken(email);

		refreshTokenRepository.save(new RefreshTokenEntity(email, refreshToken));

		System.out.println("Access Token: " + accessToken);
		System.out.println("Refresh Token: " + refreshToken);

		return oauth2User;
	}

}
