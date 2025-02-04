package com.profitkey.stock.dto.response.mypage;

import java.time.LocalDateTime;

import com.profitkey.stock.entity.Community;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageResponse {

	// 댓글 응답 DTO
	@Getter
	@Builder
	public static class CommentResponse {
		private final String commentId;        // 댓글 ID
		private final String content;          // 댓글 내용
		private final Integer writerId;        // 작성자 ID
		private final LocalDateTime createdAt; // 작성 시간
		private final LocalDateTime updatedAt; // 수정 시간
		private final long likeCount;          // 좋아요 수

		// Community 엔티티를 CommentResponse DTO로 변환하는 메서드
		public static CommentResponse fromEntity(Community community, long likeCount) {
			return CommentResponse.builder()
				.commentId(community.getId())
				.content(community.getContent())
				.writerId(community.getWriterId())
				.createdAt(community.getCreatedAt())
				.updatedAt(community.getUpdatedAt())
				.likeCount(likeCount)
				.build();
		}
	}
}
