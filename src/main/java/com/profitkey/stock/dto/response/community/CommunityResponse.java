package com.profitkey.stock.dto.response.community;

import java.time.LocalDateTime;

import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.UserInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityResponse {
	private final String id;
	private final Long writerId;
	private final String writerNickname;
	private final String writerImageUrl;
	private final String parentId;
	private final String content;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;
	private long likeCount;
	private long replieCount;

	public static CommunityResponse fromEntity(Community community, UserInfo writer, long likeCount, long replieCount) {
		return CommunityResponse.builder()
			.id(community.getId())
			.content(community.getContent())
			.writerNickname(writer.getNickname()) // nickname
			.writerImageUrl(writer.getProfileImage()) // profileImage
			.writerId(writer.getUserId()) // userId
			.likeCount(likeCount) // likeCount
			.replieCount(replieCount) // replieCount
			.createdAt(community.getCreatedAt())
			.updatedAt(community.getUpdatedAt())
			.build();
	}

}
