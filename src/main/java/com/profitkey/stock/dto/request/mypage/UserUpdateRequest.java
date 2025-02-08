package com.profitkey.stock.dto.request.mypage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

	@Schema(description = "사용자 닉네임", example = "햄스터")
	private String nickname;

	@Schema(description = "사용자 프로필 이미지 URL", example = "https://example.com/profile.jpg")
	private String profileImageUrl;
}
