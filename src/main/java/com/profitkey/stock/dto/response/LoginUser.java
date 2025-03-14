package com.profitkey.stock.dto.response;

import com.profitkey.stock.entity.Auth;
import com.profitkey.stock.entity.AuthProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUser {
	private Long id;
	private String email;
	private AuthProvider provider;
	private String nickname;

	// @Builder
	// public LoginUser(Auth auth) {
	// 	this.id = auth.getId();
	// 	this.email = auth.getEmail();
	// 	this.provider = auth.getProvider();
	// 	this.nickname = auth.getUserInfo() != null ? auth.getUserInfo().getNickname() : null; // UserInfo에서 가져오기
	// }
	@Builder
	public LoginUser(Long id, String email, AuthProvider provider, String nickname) {
		this.id = id;
		this.email = email;
		this.provider = provider;
		this.nickname = nickname; // AuthService에서 조회한 닉네임을 직접 주입
	}

	// ✅ Auth 객체와 닉네임을 받아서 LoginUser 객체 생성하는 정적 메서드 추가
	public static LoginUser from(Auth auth, String nickname) {
		return LoginUser.builder()
			.id(auth.getId())
			.email(auth.getEmail())
			.provider(auth.getProvider())
			.nickname(nickname)
			.build();
	}
}
