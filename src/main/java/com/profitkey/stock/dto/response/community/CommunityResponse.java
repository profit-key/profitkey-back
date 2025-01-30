package com.profitkey.stock.dto.response.community;

import com.profitkey.stock.entity.Community;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityResponse {
	private final String id;
	private final Integer writerId;
	private final String parentId;
	private final String content;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	public static CommunityResponse fromEntity(Community community) {
		return CommunityResponse.builder()
			.id(community.getId())
			.writerId(community.getWriterId())
			.parentId(community.getParentId())
			.content(community.getContent())
			.createdAt(community.getCreatedAt())
			.updatedAt(community.getUpdatedAt())
			.build();
	}
}
