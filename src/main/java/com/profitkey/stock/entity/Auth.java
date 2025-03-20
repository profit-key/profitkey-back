package com.profitkey.stock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Auth")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false, unique = true)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = "PROVIDER", nullable = false)
	private AuthProvider provider;

	@Column(name = "ACCESS_TOKEN", columnDefinition = "TEXT")
	private String accessToken;

	@Column(name = "KAKAO_ACCESS_TOKEN", columnDefinition = "TEXT")
	private String kakaoAccessToken;  // 카카오 액세스 토큰 추가

	@Builder
	public Auth(Long id, String email, AuthProvider provider, String accessToken, String kakaoAccessToken) {
		this.id = id;
		this.email = email;
		this.provider = provider;
		this.accessToken = accessToken;
		this.kakaoAccessToken = kakaoAccessToken;
	}
}
