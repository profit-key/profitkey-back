package com.profitkey.stock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

	// @OneToOne(mappedBy = "auth", cascade = CascadeType.ALL)
	// @JsonBackReference  // UserInfo에서 참조할 때 무한 참조 방지
	// private UserInfo userInfo;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "USER_ID")
	private UserInfo userInfo;  // 단방향 참조

	@Builder
	public Auth(String email, AuthProvider provider, String accessToken) {
		this.email = email;
		this.provider = provider;
		this.accessToken = accessToken;
	}
}
