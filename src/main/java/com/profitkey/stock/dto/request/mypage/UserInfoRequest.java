package com.profitkey.stock.dto.request.mypage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoRequest {

	@Schema(description = "사용자 ID", example = "1")
	private Long userId;

	@Schema(description = "사용자 이메일", example = "user@example.com")
	private String email;

	@Schema(description = "사용자 닉네임", example = "햄스터")
	private String nickname;

	@Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
	private String profileImageUrl;
}
