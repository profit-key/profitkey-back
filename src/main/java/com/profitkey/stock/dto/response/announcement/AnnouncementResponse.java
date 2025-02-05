package com.profitkey.stock.dto.response.announcement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "공지사항 상세조회 응답")
public class AnnouncementResponse {
	private Long id;
	private String title;
	private String content;
	private Boolean published;
	private String createdAt;

	@Builder
	public AnnouncementResponse(Long id, String title, String content, Boolean published, String createdAt) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.published = published;
		this.createdAt = createdAt;
	}
}
