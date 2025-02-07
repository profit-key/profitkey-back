package com.profitkey.stock.dto.response.announcement;

import com.profitkey.stock.dto.common.Pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "공지사항 목록 조회 응답")
public class AnnouncementListResponse {
	private AnnouncementListItem[] announcements;
	private Pagination pagination;

	@Builder
	public AnnouncementListResponse(AnnouncementListItem[] announcements, Pagination pagination) {
		this.announcements = announcements;
		this.pagination = pagination;
	}

}
