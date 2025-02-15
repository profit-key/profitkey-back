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

	@Builder
	public LoginUser(Auth auth) {
		this.id = auth.getId();
		this.email = auth.getEmail();
		this.provider = auth.getProvider();
		this.nickname = auth.getUserInfo() != null ? auth.getUserInfo().getNickname() : null; // UserInfo에서 가져오기
	}
}
