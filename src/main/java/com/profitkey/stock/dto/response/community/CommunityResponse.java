package com.profitkey.stock.dto.response.community;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.profitkey.stock.entity.Community;
import com.profitkey.stock.entity.UserInfo;
import com.profitkey.stock.service.S3UploadService;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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

	public static CommunityResponse fromEntity(Community community, UserInfo writer, long likeCount, long replieCount,
		S3UploadService s3UploadService) {

		// S3 URL 변환
		String profileImageUrl = writer.getProfileImage();
		if (profileImageUrl != null) {
			profileImageUrl = s3UploadService.getFileUrl(profileImageUrl);
		}

		return CommunityResponse.builder()
			.id(community.getId())
			.parentId(community.getParentId())
			.content(community.getContent())
			.writerNickname(writer.getNickname()) // nickname
			.writerImageUrl(profileImageUrl) // 여기 수정 (S3 URL 적용)
			.writerId(writer.getUserId()) // userId
			.likeCount(likeCount) // likeCount
			.replieCount(replieCount) // replieCount
			.createdAt(community.getCreatedAt())
			.updatedAt(community.getUpdatedAt())
			.build();
	}

}
