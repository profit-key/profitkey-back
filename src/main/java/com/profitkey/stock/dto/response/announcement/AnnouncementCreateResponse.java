package com.profitkey.stock.dto.response.announcement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "공지사항 생성 응답")
public class AnnouncementCreateResponse {
	private Long id;
	private String title;
	private String content;
	private Boolean published;

	@Builder
	public AnnouncementCreateResponse(Long id, String title, String content, Boolean published) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.published = published;
	}
}
