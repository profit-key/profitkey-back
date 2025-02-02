package com.profitkey.stock.dto.request.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeRequest {
	@Schema(description = "댓글 식별자", example = "202501011234560001")
	private String commentId;
	@Schema(description = "클릭한 사용자 식별자", example = "0")
	private String userId;
	@Schema(description = "좋아요 여부", example = "false")
	private boolean isLiked;
}
