package com.profitkey.stock.dto.response.mypage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoResponse {

	@Schema(description = "사용자 이메일", example = "user@example.com")
	private String email;

	@Schema(description = "사용자 닉네임", example = "햄스터")
	private String nickname;

	@Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
	private String profileImageUrl;
}
