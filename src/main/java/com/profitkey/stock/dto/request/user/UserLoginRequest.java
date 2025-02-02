package com.profitkey.stock.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequest {

	@Schema(description = "사용자 이메일", example = "\"user@example.com\"")
	private String email;

	@Schema(description = "소셜 로그인 제공자", example = "\"KAKAO\"")
	private String provider;

	public UserLoginRequest(String email, String provider) {
		this.email = email;
		this.provider = provider;
	}
}
