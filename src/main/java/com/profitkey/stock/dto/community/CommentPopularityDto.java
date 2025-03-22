package com.profitkey.stock.dto.community;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentPopularityDto {
	private final String commentId; //댓글 id
	private final Long likeCount; //좋아요 카운트
}
