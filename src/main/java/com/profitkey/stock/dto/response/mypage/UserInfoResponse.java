package com.profitkey.stock.dto.response.mypage;

import com.profitkey.stock.entity.UserInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoResponse {

	@Schema(description = "사용자 이메일", example = "user@example.com")
	private String email;

	@Schema(description = "사용자 닉네임", example = "햄스터")
	private String nickname;

	@Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
	private String profileImageUrl;

	@Builder
	public UserInfoResponse(String email, String nickname, String profileImageUrl) {
		this.email = email;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}

	// 엔티티 -> DTO 변환 메서드
	public static UserInfoResponse fromEntity(UserInfo userInfo) {
		return UserInfoResponse.builder()
			.email(userInfo.getAuth().getEmail()) // Auth에서 이메일 가져오기
			.nickname(userInfo.getNickname())
			.profileImageUrl(userInfo.getProfileImage())
			.build();
	}
}
