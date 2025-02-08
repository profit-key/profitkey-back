package com.profitkey.stock.dto.response.mypage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyPageCommunityResponse {

	@Schema(description = "댓글 ID", example = "20250208ABC1234")
	private String id;

	@Schema(description = "댓글 내용", example = "ㄷㄷ 매수각이네요")
	private String content;

	@Schema(description = "댓글 작성 시간", example = "2025-02-08T15:29:04")
	private String createdAt;

	@Schema(description = "작성자 닉네임", example = "햄스터")
	private String nickname;

	public MyPageCommunityResponse(String id, String content, String createdAt, String nickname) {
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.nickname = nickname;
	}

}
