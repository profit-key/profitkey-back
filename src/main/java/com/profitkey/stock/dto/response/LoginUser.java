package com.profitkey.stock.dto.response;

import com.profitkey.stock.entity.AuthProvider;
import com.profitkey.stock.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUser {
	private Long id;
	private String email;
	private AuthProvider provider;
	private String nickname;

	public LoginUser(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.provider = user.getProvider();
		this.nickname = user.getNickname();
	}
}
